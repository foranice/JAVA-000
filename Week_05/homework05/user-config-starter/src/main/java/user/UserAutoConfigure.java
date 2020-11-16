package user;

import io.kimmking.spring01.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import user.properties.StudentProperties;

@Configuration
@ComponentScan("user.properties")
public class UserAutoConfigure {
    public UserAutoConfigure(){
        System.out.println("aaa");
    }
    @Autowired
    StudentProperties studentProperties;
    @Bean
    @ConditionalOnMissingBean(Student.class)
    public Student getStudent() {
        Student student = new Student();
        student.init();
        student.setId(studentProperties.getId());
        student.setName(studentProperties.getName());
        return student;
    }
}
