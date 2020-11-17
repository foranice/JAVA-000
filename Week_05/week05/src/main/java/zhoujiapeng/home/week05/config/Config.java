package zhoujiapeng.home.week05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zhoujiapeng.home.week05.beans.DemoImpl;
import zhoujiapeng.home.week05.beans.DemoInterface;


@Configuration
public class Config {
    @Bean(name = "JavaConfig")
    public DemoInterface createDemoBean(){
        DemoImpl demo = new DemoImpl();
        demo.setText("load from java config");
        return demo;
    }
}
