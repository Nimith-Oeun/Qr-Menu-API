package persional.qr_menu.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis Configuration for QR Menu API
 * Configures Redis connection, caching, and serialization
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Value(("${spring.data.redis.host}"))
    private String redisHost;

    @Value(("${spring.data.redis.port}"))
    private int redisPort;

    /**
     * Configure Redis Connection Factory with Lettuce client
     */
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        // Configure client options for better performance
//        ClientOptions clientOptions = ClientOptions.builder()
//                .socketOptions(SocketOptions.builder()
//                        .connectTimeout(Duration.ofSeconds(10))
//                        .keepAlive(true)
//                        .build())
//                .build();
//
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .clientOptions(clientOptions)
//                .commandTimeout(Duration.ofSeconds(5))
//                .build();
//
//        // Connection factory with localhost:6379 (default Redis)
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(
//                new org.springframework.data.redis.connection.RedisStandaloneConfiguration("localhost", 6379),
//                clientConfig);
//
//        factory.setValidateConnection(true);
//        return factory;
//    }
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        return  configuration;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory(redisStandaloneConfiguration());
    }

    /**
     * Configure RedisTemplate for general Redis operations
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        //Key
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //Value
        redisTemplate.setValueSerializer(this.jackson2JsonRedisSerializer());
        //value hashmap
        redisTemplate.setHashValueSerializer(this.jackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    protected Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return new Jackson2JsonRedisSerializer<>(objectMapper,Object.class);
    }

    /**
     * Configure Redis Cache Manager with different TTL for different cache types
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Default configuration with Java 8 time support
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // Default 1 hour TTL
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer())
                )
                .disableCachingNullValues();

        // Specific configurations for different cache types
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // Menu data - cached for 2 hours (changes infrequently)
        cacheConfigurations.put("menu", defaultConfig.entryTtl(Duration.ofDays(7)));
        cacheConfigurations.put("menuByCategory", defaultConfig.entryTtl(Duration.ofDays(7)));
        cacheConfigurations.put("menuSeparated", defaultConfig.entryTtl(Duration.ofDays(7)));
        
        // Individual items - cached for 30 minutes (may change more frequently)
        cacheConfigurations.put("items", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        
        // Statistics and metrics - cached for 5 minutes (should be relatively fresh)
        cacheConfigurations.put("stats", defaultConfig.entryTtl(Duration.ofMinutes(5)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations) // Apply specific configurations
                .transactionAware() // Enable transaction awareness
                .build();
    }
}