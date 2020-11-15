package zhoujiapeng.beans;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component("Annotation")
@Data
public class DemoImpl implements DemoInterface {
    String text="load from annotation";
    @Override
    public String get() {
        return text;
    }
}
