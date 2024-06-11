package org.dylan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Redis 启动类
 *
 * @author Dylan
 */
@SpringBootApplication
public class QuickRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickRedisApplication.class, args);
    }
}
