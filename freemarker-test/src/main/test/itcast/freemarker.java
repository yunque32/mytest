package itcast;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class freemarker {

    @Test
    public void test() throws IOException, TemplateException {

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(freemarker.class,"/ftl");
        configuration.setDefaultEncoding("UTF-8");
        Template template = configuration.getTemplate("hello.ftl");
        Map<String, Object> map = new HashMap<>();
        map.put("name","shenming");
        map.put("message","欢迎来到mimi世界");
        template.process(map,
                new PrintWriter(System.out));

    }


}
