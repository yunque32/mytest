package cn.itcast.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class Redis01Test {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void Te(){
        redisTemplate.boundValueOps("qingfen").set("shendong");

    }
    @Test
    public void T2(){
        redisTemplate.delete("shenmi");
    }

    @Test
    public void T3(){
        redisTemplate.boundListOps("name1").leftPush("zuo1");
        redisTemplate.boundListOps("name1").leftPush("zuo2");
        redisTemplate.boundListOps("name1").leftPush("zuo3");

        List<Object> name1 = redisTemplate.boundListOps("name1").range(0, 1);
        System.out.println(name1);
    }
}
