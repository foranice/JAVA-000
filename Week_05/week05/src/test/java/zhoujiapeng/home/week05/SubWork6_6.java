package zhoujiapeng.home.week05;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhoujiapeng.home.week05.entity.User;
import zhoujiapeng.home.week05.service.UserService;

import java.util.List;


@SpringBootTest( classes=  Week05Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SubWork6_6 {
    @Autowired
    @Qualifier("hikari")
    UserService HikariUserService;
    @Autowired
    @Qualifier("jbdc")
    UserService JbdcUserService;
    @Autowired
    @Qualifier("jbdc-pre")
    UserService JbdcUserPreService;
    @Test
    public void runJbdc(){
        run(JbdcUserService);

    }
    @Test
    public void runJbdcPre(){
        run(JbdcUserPreService);

    }
    @Test
    public void runJHikari(){
        run(HikariUserService);

    }
    private void run(UserService service){
        List userList;
        User user1 = new User();
        user1.setId(1234);
        user1.setName("哈哈哈哈");
        user1.setAge(30);
        User user2 = new User();
        user2.setId(2345);
        user2.setName("嘿嘿嘿嘿");
        user2.setAge(32);

        service.add(user1);
        service.add(user2);
        userList = service.queryAll();
        System.out.println(userList);

        service.deleteById(1234);
        userList = service.queryAll();
        System.out.println(userList);

        service.alterName(2345,"呃呃呃呃");
        userList = service.queryAll();
        System.out.println(userList);
    }
}

