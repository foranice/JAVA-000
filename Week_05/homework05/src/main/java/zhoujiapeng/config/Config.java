package zhoujiapeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zhoujiapeng.beans.DemoImpl;
import zhoujiapeng.beans.DemoInterface;

@Configuration
public class Config {
    @Bean(name = "JavaConfig")
    public DemoInterface createDemoBean(){
        DemoImpl demo = new DemoImpl();
        demo.setText("load from java config");
        return demo;
    }
}
