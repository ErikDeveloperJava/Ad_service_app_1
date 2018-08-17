package yourAd.manager;

import lombok.Cleanup;
import lombok.Data;
import yourAd.db.ConnectionProvider;
import yourAd.exception.DatabaseException;
import yourAd.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    private static final String INSERT = "insert into category(name,parent_id)values(?,?)";
    private static final String SELECT_BY_PARENT_IS_NULL = "select * from category where parent_id is null";
    private static final String SELECT_ALL = "select * from category ";
    private static final String SELECT_BY_PARENT = "select * from category where parent_id=?";
    private static final String SELECT_BY_ID = "select * from category where id=?";
    private static final String SELECT_BY_AD_ID = "select c.id,c.name,c.parent_id from category c inner join ad a on " +
            "a.id=? and c.id=a.category_id";

    private Connection connection;

    public CategoryManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public List<Category> getAll(){
        List<Category> categories = new ArrayList<Category>();
        try {
            @Cleanup Statement statement = connection.createStatement();
            @Cleanup ResultSet resultSet = statement.executeQuery(SELECT_BY_PARENT_IS_NULL);
            while (resultSet.next()) {
                Category category = getCategory(resultSet);
                category.setChildrenList(getChildrenList(category.getId()));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return categories;
    }

    public Category getById(int id){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1,id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return getCategory(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();

        }
        return null;
    }

    public List<Category> getAllByChildrenListIsEmpty(){
        List<Category> categories = new ArrayList<Category>();
        try {
            @Cleanup Statement statement = connection.createStatement();
            @Cleanup ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                if(!isChildrenListEmpty(resultSet.getInt("id"))){
                    Category category = getCategory(resultSet);
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return categories;
    }

    public void add(Category category){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,category.getName());
            preparedStatement.setObject(2,category.getParent().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public Category getByAdId(int adId){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_AD_ID);
            preparedStatement.setInt(1,adId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Category category = getCategory(resultSet);
                setParents(category.getParent());
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return null;
    }

    private void setParents(Category category){
        if(category.getId() == null){
            return;
        }
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1,category.getId());
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                category.setName(resultSet.getString("name"));
                category.setParent(Category.builder().id((Integer) resultSet.getObject("parent_id")).build());
                setParents(category.getParent());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();

        }
    }

    private boolean isChildrenListEmpty(int id){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PARENT);
            preparedStatement.setInt(1,id);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public List<Category> getChildrenList(int parentId){
        List<Category> childrenList = new ArrayList<Category>();
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_PARENT);
            preparedStatement.setInt(1,parentId);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Category category = getCategory(resultSet);
                category.setChildrenList(getChildrenList(category.getId()));
                childrenList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return childrenList;
    }

    private Category getCategory(ResultSet resultSet) throws SQLException {
        return Category.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .parent(Category.builder().id(resultSet.getInt("parent_id")).build())
                .build();
    }
}
