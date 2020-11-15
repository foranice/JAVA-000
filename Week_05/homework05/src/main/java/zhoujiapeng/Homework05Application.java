package zhoujiapeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:beans.xml"})
@SpringBootApplication
public class Homework05Application {
    public static void main(String[] args) {

        SpringApplication.run(Homework05Application.class, args
        );
    }

}
