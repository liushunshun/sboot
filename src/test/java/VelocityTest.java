import com.xy.Application;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.test.context.junit4.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XiuYang on 2016/10/25.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class VelocityTest {
    @Autowired
    VelocityEngine velocityEngine;

    @Test
    public void velocityTest(){
        Map<String, Object> model = new HashMap<>();
        model.put("uname", "Alex");
        System.out.println(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "index.vm", "UTF-8", model));
    }
}
