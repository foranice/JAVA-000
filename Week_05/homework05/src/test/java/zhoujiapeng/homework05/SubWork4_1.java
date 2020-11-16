package zhoujiapeng.homework05;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import zhoujiapeng.Homework05Application;
import zhoujiapeng.beans.DemoImpl;
import zhoujiapeng.beans.DemoInterface;

@SpringBootTest(classes= Homework05Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SubWork4_1 {
    @Autowired
    @Qualifier("Xml")
    private DemoInterface xmlDemo;
    @Autowired
    @Qualifier("Annotation")
    private DemoInterface annotationDemo;
    @Autowired
    @Qualifier("JavaConfig")
    private DemoInterface javaConfigDemo;
    @Test
    void run(){
        assert xmlDemo.get().equals("load from xml");
        System.out.println(xmlDemo.get());
        assert annotationDemo.get().equals("load from annotation");
        System.out.println(annotationDemo.get());
        assert javaConfigDemo.get().equals("load from java config");
        System.out.println(javaConfigDemo.get());
    }

}
