package zhoujiapeng.home.week05.service;

import zhoujiapeng.home.week05.entity.User;

import java.util.List;

public interface UserService {
    boolean add(User user);
    boolean deleteById(Integer userId);
    boolean alterName(Integer userId,String newName);
    List<User> queryAll();
}
