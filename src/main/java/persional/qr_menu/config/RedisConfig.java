package persional.qr_menu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
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

    /**
     * Configure Redis Connection Factory with Lettuce client
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // Configure client options for better performance
        ClientOptions clientOptions = ClientOptions.builder()
                .socketOptions(SocketOptions.builder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .keepAlive(true)
                        .build())
                .build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofSeconds(5))
                .build();

        // Connection factory with localhost:6379 (default Redis)
        LettuceConnectionFactory factory = new LettuceConnectionFactory(
                new org.springframework.data.redis.connection.RedisStandaloneConfiguration("localhost", 6379),
                clientConfig);
        
        factory.setValidateConnection(true);
        return factory;
    }

    /**
     * Configure RedisTemplate for general Redis operations
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Configure ObjectMapper for Java 8 time support
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();

        // Use String serializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Use JSON serializer for values with Java 8 time support
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * Configure Redis Cache Manager with different TTL for different cache types
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Configure ObjectMapper for Java 8 time support
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();

        // Default configuration with Java 8 time support
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // Default 1 hour TTL
                .serializeKeysWith(org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)))
                .disableCachingNullValues();

        // Specific configurations for different cache types
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // Menu data - cached for 2 hours (changes infrequently)
        cacheConfigurations.put("menu", defaultConfig.entryTtl(Duration.ofHours(2)));
        cacheConfigurations.put("menuByCategory", defaultConfig.entryTtl(Duration.ofHours(2)));
        cacheConfigurations.put("menuSeparated", defaultConfig.entryTtl(Duration.ofHours(2)));
        
        // Individual items - cached for 30 minutes (may change more frequently)
        cacheConfigurations.put("items", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        
        // Statistics and metrics - cached for 5 minutes (should be relatively fresh)
        cacheConfigurations.put("stats", defaultConfig.entryTtl(Duration.ofMinutes(5)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .transactionAware()
                .build();
    }
}