package com.jpa.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
//@ConditionalOnProperty(name = "cache.redis.autoConfig", havingValue = "true")
public class RedisConfig {
    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.username}")
    private String username;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.timeout:5000}")
    private Integer timeout;

    @Bean("redisConnectionFactory")
    public RedisConnectionFactory redisFactory() {
//        var clusterConfig = new RedisClusterConfiguration();

//        clusterConfig.setUsername(username);
//        clusterConfig.setPassword(RedisPassword.of(password));
//        List<String> nodes = Arrays.asList("192.168.117.234");
//        for (String node : nodes) {
//            clusterConfig.clusterNode(node, port);
//        }
        var config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
//        config.setUsername(username);
//        config.setPassword(password);

        var clientConfig = LettuceClientConfiguration.builder()
                        .commandTimeout(Duration.ofMillis(timeout))
                        .build();
        return new LettuceConnectionFactory(config, clientConfig);
    }

}
