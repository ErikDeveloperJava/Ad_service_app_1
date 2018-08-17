package yourAd.manager;

import lombok.Cleanup;
import yourAd.db.ConnectionProvider;
import yourAd.exception.DatabaseException;
import yourAd.form.ImageForm;
import yourAd.model.Ad;
import yourAd.model.Category;
import yourAd.model.Image;
import yourAd.model.User;
import yourAd.util.ImageUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdManager {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String INSERT = "insert into ad(title,description,price,imgUrl,category_id,user_id,created_date)values(?,?,?,?,?,?,?)";
    private static final String UPDATE= "update ad set imgUrl=? where id=?";
    private static final String SELECT_COUNT = "select count(*) from ad";
    private static final String SELECT_COUNT_BY_CATEGORY_ID = "select count(*) from ad a inner join category c on " +
            "a.category_id=c.id and c.id=?";
    private static final String SELECT_ALL_BY_CATEGORY_ID = "select a.id as adId,a.title,a.description,a.price,a.imgUrl,a.created_date from" +
            " ad a inner join category c on " +
            "a.category_id=c.id and c.id=?";
    private static final String SELECT_ALL = "select * from ad order by created_date desc limit ?,?";
    private static final String SELECT_BY_ID = "select a.id as adId,a.title,a.description,a.price,a.imgUrl,a.created_date," +
            "u.id as userId,u.name,u.surname,u.username,u.password from " +
            "ad a inner join user u" +
            " on a.user_id=u.id and a.id=?";
    private static final String SELECT_COUNT_BY_USER_ID = "select count(*) from ad a inner join user u on " +
            "a.user_id=u.id and u.id=?";
    private static final String SELECT_ALL_BY_USER_ID = "select a.id as adId,a.title,a.description,a.price,a.imgUrl,a.created_date" +
            " from ad a inner join user u on " +
            "a.user_id=u.id and u.id=?";
    private static final String SELECT_COUNT_BY_TITLE = "select count(*) from ad where title like ?";
    private static final String SELECT_ALL_BY_TITLE = "select * from ad where title like ?";

    private Connection connection;

    public AdManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public void add(Ad ad, ImageForm imageForm,ImageManager imageManager){
        String parent = "";
        String imgName = "";
        String imgUrl = "";
        try {
            connection.setAutoCommit(false);
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,ad.getTitle());
            preparedStatement.setString(2,ad.getDescription());
            preparedStatement.setString(3,String.valueOf(ad.getPrice()));
            preparedStatement.setString(4," ");
            preparedStatement.setInt(5,ad.getCategory().getId());
            preparedStatement.setInt(6,ad.getUser().getId());
            preparedStatement.setString(7,DATE_FORMAT.format(ad.getCreatedDate()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                ad.setId(resultSet.getInt(1));
            }else {
                throw new DatabaseException();
            }
            parent = String.valueOf(ad.getId());
            imgName= System.currentTimeMillis() + imageForm.getName();
            imgUrl = ad.getUser().getUsername() + "/" + parent + "/" + imgName;
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,imgUrl);
            preparedStatement.setInt(2,ad.getId());
            preparedStatement.execute();
            Image image = Image.builder()
                    .url(imgUrl)
                    .ad(ad)
                    .build();
            imageManager.add(image);
            try {
                ImageUtil.save(ad.getUser().getUsername() + "/" + parent,imgName,imageForm.getBytes());
            }catch (Exception e){
                ImageUtil.delete(ad.getUser().getUsername());
                ImageUtil.delete(parent);
                ImageUtil.delete(imgUrl);
                throw e;
            }

            connection.commit();
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

    public int getCountByCategoryId(int categoryId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_BY_CATEGORY_ID);
            preparedStatement.setInt(1,categoryId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return 0;
    }

    public List<Ad> getByAllCategoryId(int parentId,List<Category> childrens){
        List<Ad> adList = new ArrayList<Ad>();
        adList.addAll(getByCategoryId(parentId));
        for (Category category : childrens) {
            adList.addAll(getByCategoryId(category.getId()));
        }
        return adList;
    }

    public int getCountByUserId(int userId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return 0;
    }

    public List<Ad> getAllByUserId(int userId){
        List<Ad> adList = new ArrayList<Ad>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                adList.add(getAd(resultSet,true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return adList;
    }

    public int getCountByTitle(String title){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_BY_TITLE);
            preparedStatement.setString(1,"%" + title + "%");
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return 0;
    }



    public List<Ad> getAllByTitle(String title){
        List<Ad> adList = new ArrayList<Ad>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_TITLE);
            preparedStatement.setString(1,"%" + title + "%");
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                adList.add(getAd(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return adList;
    }


    private List<Ad> getByCategoryId(int categoryId){
        List<Ad> adList = new ArrayList<Ad>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_CATEGORY_ID);
            preparedStatement.setInt(1,categoryId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                adList.add(getAd(resultSet,true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return adList;
    }

    public int getCount(){
        try {
            @Cleanup Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT);
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return 0;
    }

    public List<Ad> getAll(int pageNumber,int size){
        List<Ad> adList = new ArrayList<Ad>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            preparedStatement.setInt(1,pageNumber);
            preparedStatement.setInt(2,size);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                adList.add(getAd(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();

        }
        return adList;
    }

    private Ad getAd(ResultSet resultSet,boolean...token) throws SQLException {
        return Ad.builder()
                .id(resultSet.getInt(token.length > 0 ? "adId" : "id"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .price(resultSet.getDouble("price"))
                .createdDate(resultSet.getDate("created_date"))
                .imgUrl(resultSet.getString("imgUrl"))
                .build();
    }

    public Ad getById(int id){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1,id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Ad ad = getAd(resultSet,true);
                User user = User.builder()
                        .id(resultSet.getInt("userId"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                ad.setUser(user);
                return ad;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return null;
    }
}
