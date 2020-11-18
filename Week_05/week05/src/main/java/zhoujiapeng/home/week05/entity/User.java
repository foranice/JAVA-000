package zhoujiapeng.home.week05.entity;

import lombok.Data;

import java.util.Map;

@Data
public class User {
    private Integer id;
    private String name;
    private Integer age;
    public User(){}
    public User(Map param) {
        this.id = (Integer) param.get("ID");
        this.name = (String)param.get("NAME");
        this.age = (Integer)(param.get("AGE"));
    }
}
