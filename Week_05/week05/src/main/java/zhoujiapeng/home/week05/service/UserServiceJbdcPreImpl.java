package zhoujiapeng.home.week05.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zhoujiapeng.home.week05.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Component("jbdc-pre")
public class UserServiceJbdcPreImpl implements UserService{
    @Value("#{dataSource.jdbcUrl}")
    String URL ;
    @Value("#{dataSource.username}")
    String USER ;
    @Value("#{dataSource.password}")
    String PASSWORD ;
    @Value("#{dataSource.driverClassName}")
    String DRIVER_CLASS ;
    @Override
    public boolean add(User user) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String sql = "insert into user (id,name,age) values (?,?,?)";
            PreparedStatement ps =conn.prepareStatement(sql);
            ps.setInt(1,user.getId());
            ps.setString(2,user.getName());
            ps.setInt(3,user.getAge());
            ps.execute();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(Integer userId) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String delete = "delete from user where id = ?";
            PreparedStatement ps =conn.prepareStatement(delete);
            ps.setInt(1,userId);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean alterName(Integer userId, String newName) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String update = "update user set name = ? where id = ?";
            PreparedStatement ps =conn.prepareStatement(update);
            ps.setString(1,newName);
            ps.setInt(2,userId);
            conn.createStatement().execute(update);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<User> queryAll() {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
            String query="select * from user";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<User> userList = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(1));
                user.setAge(rs.getInt(3));
                userList.add(user);
            }
            return userList;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
