package zhoujiapeng.home.week05;

import io.kimmking.aop.ISchool;
import io.kimmking.spring01.Student;
import io.kimmking.spring02.Klass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest(classes=  Week05Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SubWork6_4 {
    @Resource(name = "student123")
    Student student;
    @Autowired
    Klass klass;
    @Autowired
    ISchool school;
    @Test
    public void run(){
       // student.getId();
        System.out.println(student);
        System.out.println(klass);
        klass.dong();
        System.out.println(school);
        school.ding();
    }
}
