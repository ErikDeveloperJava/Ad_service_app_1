package yourAd.manager;

import lombok.Cleanup;
import yourAd.db.ConnectionProvider;
import yourAd.exception.DatabaseException;
import yourAd.model.User;
import yourAd.model.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String INSERT = "insert into user(name,surname,username,password,role) values(?,?,?,?,?)";
    private static final String SELECT_BY_USERNAME = "select * from user where username=?";
    private static final String SELECT_ALL = "select * from user";
    private static final String DELETE_BY_ID = "delete from user where id=?";

    private Connection connection;

    public UserManager() {
        connection = ConnectionProvider.getInstance().getConnection();
    }

    public void add(User user){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getSurname());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5,user.getRole().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }

    public User getByUsername(String username){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USERNAME);
            preparedStatement.setString(1,username);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return getUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return null;
    }

    public List<User> getAll(){
        List<User> users = new ArrayList<User>();
        try {
            @Cleanup Statement statement = connection.createStatement();
            @Cleanup ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                users.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
        return users;
    }

    public void deleteById(int id){
        try {
            @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUser(ResultSet resultSet)throws SQLException{
        return User.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .surname(resultSet.getString("surname"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .role(UserRole.valueOf(resultSet.getString("role")))
                .build();
    }
}
