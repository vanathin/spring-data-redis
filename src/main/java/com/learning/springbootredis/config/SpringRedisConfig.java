package com.learning.springbootredis.config;

import com.learning.springbootredis.bean.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration

@PropertySource("classpath:./application.properties")
public class SpringRedisConfig {


    @Value("${redis.hostname}")
    private String redisHostName;

    @Value("${redis.port}")
    private int redisPort;




    //Jedis is Java redis client.
    //Redis single threaded
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        LettuceConnectionFactory lettuce = new LettuceConnectionFactory();
        return lettuce;
    }

    //asynchronous operations and is thread-safe, unlike Jedis
    @Bean
    RedisTemplate<String, Student> redisTemplate() {
        RedisTemplate<String, Student> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return redisTemplate;
    }
}
