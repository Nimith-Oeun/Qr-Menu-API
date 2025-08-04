package persional.qr_menu.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import persional.qr_menu.service.CacheService;

import java.util.Objects;
import java.util.Set;

/**
 * Implementation of CacheService for Redis cache operations
 */
@Service
public class CacheServiceImpl implements CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void clearAllMenuCaches() {
        try {
            logger.info("Clearing all menu-related caches");
            
            // Clear specific menu caches
            clearCache("menu");
            clearCache("menuByCategory");
            clearCache("separatedMenu");
            clearCache("menuItem");
            
            logger.info("Successfully cleared all menu caches");
        } catch (Exception e) {
            logger.error("Error clearing menu caches: {}", e.getMessage(), e);
        }
    }

    @Override
    public void clearCategoryCache(String category) {
        try {
            logger.info("Clearing cache for category: {}", category);
            
            // Clear the specific category cache
            if (cacheManager.getCache("menuByCategory") != null) {
                Objects.requireNonNull(cacheManager.getCache("menuByCategory")).evict(category);
            }
            
            // Also clear general menu caches as they might contain this category
            clearCache("menu");
            clearCache("separatedMenu");
            
            logger.info("Successfully cleared cache for category: {}", category);
        } catch (Exception e) {
            logger.error("Error clearing cache for category {}: {}", category, e.getMessage(), e);
        }
    }

    @Override
    public void clearCacheByPattern(String pattern) {
        try {
            logger.info("Clearing cache by pattern: {}", pattern);
            
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                logger.info("Cleared {} cache entries matching pattern: {}", keys.size(), pattern);
            } else {
                logger.info("No cache entries found matching pattern: {}", pattern);
            }
        } catch (Exception e) {
            logger.error("Error clearing cache by pattern {}: {}", pattern, e.getMessage(), e);
        }
    }

    @Override
    public boolean isCacheHealthy() {
        try {
            // Test Redis connection by performing a simple operation
            redisTemplate.opsForValue().set("health-check", "test", java.time.Duration.ofSeconds(10));
            String result = (String) redisTemplate.opsForValue().get("health-check");
            redisTemplate.delete("health-check");
            
            boolean isHealthy = "test".equals(result);
            logger.debug("Cache health check result: {}", isHealthy);
            return isHealthy;
        } catch (Exception e) {
            logger.error("Cache health check failed: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public String getCacheStats() {
        try {
            StringBuilder stats = new StringBuilder();
            stats.append("Cache Statistics:\n");
            
            // Get Redis info
            stats.append("Redis Connection: ").append(isCacheHealthy() ? "Connected" : "Disconnected").append("\n");
            
            // Check cache managers
            if (cacheManager != null) {
                stats.append("Cache Manager: ").append(cacheManager.getClass().getSimpleName()).append("\n");
                stats.append("Available Caches: ").append(cacheManager.getCacheNames()).append("\n");
            }
            
            // Get approximate key count (this is an estimate)
            try {
                Set<String> keys = redisTemplate.keys("*");
                stats.append("Approximate Total Keys: ").append(keys != null ? keys.size() : 0).append("\n");
            } catch (Exception e) {
                stats.append("Could not retrieve key count: ").append(e.getMessage()).append("\n");
            }
            
            return stats.toString();
        } catch (Exception e) {
            logger.error("Error getting cache statistics: {}", e.getMessage(), e);
            return "Error retrieving cache statistics: " + e.getMessage();
        }
    }

    @Override
    public void warmUpCache() {
        try {
            logger.info("Starting cache warm-up process");
            
            // This would typically involve loading frequently accessed data
            // For now, we'll just log that the warm-up process has started
            // You can implement specific warm-up logic based on your business needs
            
            logger.info("Cache warm-up process initiated. Implement specific warm-up logic as needed.");
        } catch (Exception e) {
            logger.error("Error during cache warm-up: {}", e.getMessage(), e);
        }
    }

    /**
     * Helper method to clear a specific cache
     */
    private void clearCache(String cacheName) {
        try {
            if (cacheManager.getCache(cacheName) != null) {
                Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
                logger.debug("Cleared cache: {}", cacheName);
            }
        } catch (Exception e) {
            logger.error("Error clearing cache {}: {}", cacheName, e.getMessage(), e);
        }
    }
}
