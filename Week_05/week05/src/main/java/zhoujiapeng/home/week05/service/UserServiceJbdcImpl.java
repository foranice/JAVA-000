package zhoujiapeng.home.week05.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;
import zhoujiapeng.home.week05.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("jbdc")
public class UserServiceJbdcImpl implements UserService{
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
            String insert=String.format("insert into user (id,name,age) values (%d,%s,%d)",user.getId(),user.getName(),user.getAge());
            conn.createStatement().execute(insert);
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
            String delete = String.format("delete from user where id = %d",userId);
            conn.createStatement().execute(delete);
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
            String update = String.format("update user set name = %s where id = %d",newName,userId);
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
            ResultSet rs = conn.createStatement().executeQuery(query);
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
