package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

@SpringBootTest
class LearnRabbitMqApplicationTests {

    @Test
    void contextLoads() {


            if(!Pattern.matches("^1[3-9]\\d{9}$","13802026020")){
                System.out.println("请求的手机号格式错误");
            }else{
                System.out.println("请求的手机号正确");
            }
    }

}
