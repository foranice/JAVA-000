package zhoujiapeng.home.week05.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;
import zhoujiapeng.home.week05.entity.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("hikari")
public class UserServiceHikariImpl implements UserService {
    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Override
    public boolean add(User user) {
        try{
            jdbcTemplate.update("insert into user (id,name,age) values (?,?,?)",new Object[]{user.getId(),user.getName(),user.getAge()});
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Integer userId) {
        try{
            jdbcTemplate.update("delete from user where id = ?",new Object[]{userId});
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean alterName(Integer userId, String newName) {
        try{
            jdbcTemplate.update("update user set name = ? where id = ?",new Object[]{newName,userId});
            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> queryAll() {
        List rs = jdbcTemplate.queryForList("SELECT * FROM user");
        List<User> list = (List<User>) rs.stream().map(e->new User((Map)e)).collect(Collectors.toList());
        return list;
    }
}
