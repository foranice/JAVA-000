package user;

import io.kimmking.aop.ISchool;
import io.kimmking.spring01.Student;
import io.kimmking.spring02.Klass;
import io.kimmking.spring02.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import user.properties.KlassProperties;
import user.properties.StudentProperties;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("user.properties")
public class UserAutoConfigure {
    public UserAutoConfigure(){
        System.out.println("aaa");
    }
    @Autowired
    StudentProperties studentProperties;
    @Autowired
    KlassProperties klassProperties;
    @Bean(name="student123")
    public Student getStudent() {
        Student student = new Student();
        student.init();
        if(studentProperties!=null){
            student.setId(studentProperties.getId());
            student.setName(studentProperties.getName());
        }
        return student;
    }
    @Bean(name="student100")
    public Student getStudent100() {
        Student student = new Student();
        student.setId(100);
        student.setName("KK100");
        student.init();
        return student;
    }
    @Bean
    @ConditionalOnMissingBean(Klass.class)
    public Klass getKlass(){
        Klass klass = new Klass();
        List students = klassProperties.getStudents().stream().map(p->{
            Student st = new Student();
            st.init();
            st.setId(p.getId());
            st.setName(p.getName());
            return st;
        }).collect(Collectors.toList());
        klass.setStudents(students);
        return klass;
    }
    @Bean
    @ConditionalOnBean(Klass.class)
    public ISchool getSchool(){
        return new School();
    };
}
