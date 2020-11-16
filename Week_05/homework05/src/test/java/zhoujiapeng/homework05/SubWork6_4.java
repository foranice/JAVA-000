package zhoujiapeng.homework05;

import io.kimmking.spring01.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhoujiapeng.Homework05Application;

@SpringBootTest(classes= Homework05Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SubWork6_4 {
    @Autowired
    Student student;
    @Test
    public void run(){
        student.getId();
        System.out.println(student);
    }
}
