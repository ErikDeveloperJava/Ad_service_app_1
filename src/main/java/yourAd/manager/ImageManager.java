package yourAd.manager;

import lombok.Cleanup;
import yourAd.db.ConnectionProvider;
import yourAd.exception.DatabaseException;
import yourAd.form.ImageForm;
import yourAd.model.Ad;
import yourAd.model.Image;
import yourAd.util.ImageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageManager {

    private static final String INSERT = "insert into image(url,ad_id)values(?,?)";
    private static final String SELECT_ALL_BY_AD_ID = "select i.id,i.url from " +
            "image i inner join ad a on i.ad_id=a.id and a.id=?";

    private Connection connection;

    public ImageManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public void add(Image image){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,image.getUrl());
            preparedStatement.setInt(2,image.getAd().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public List<Image> getAllByAdId(int adId){
        List<Image> images = new ArrayList<Image>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_AD_ID);
            preparedStatement.setInt(1,adId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                images.add(getImage(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return images;
    }

    public void addALL(String username, ImageForm imageForm,int adId){
        String imgName = System.currentTimeMillis() + imageForm.getName();
        String  imgUrl = username + "/" + adId + "/" + imgName;
        Image image = Image.builder()
                .url(imgUrl)
                .ad(Ad.builder().id(adId).build())
                .build();
        try {
            connection.setAutoCommit(false);
            add(image);
            try {
                ImageUtil.save(username + "\\" + adId,imgName,imageForm.getBytes());
                connection.commit();
            }catch (Exception e){
                ImageUtil.delete(imgName);
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DatabaseException();
        }
    }

    private Image getImage(ResultSet resultSet)throws SQLException{
        return Image.builder()
                .id(resultSet.getInt("id"))
                .url(resultSet.getString("url"))
                .build();
    }
}
