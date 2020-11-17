package zhoujiapeng.home.week05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:beans.xml"})
@SpringBootApplication
public class Week05Application {

    public static void main(String[] args) {
        SpringApplication.run(Week05Application.class, args);
    }

}
