package persional.qr_menu.service;

/**
 * Service interface for cache operations
 * Handles Redis cache management
 */
public interface CacheService {

    /**
     * Clear all menu-related caches
     */
    void clearAllMenuCaches();

    /**
     * Clear cache for specific category
     * @param category The category cache to clear
     */
    void clearCategoryCache(String category);

    /**
     * Clear cache by key pattern
     * @param pattern The cache key pattern to clear
     */
    void clearCacheByPattern(String pattern);

    /**
     * Check if cache is healthy and accessible
     * @return true if cache is working, false otherwise
     */
    boolean isCacheHealthy();

    /**
     * Get cache statistics
     * @return Cache statistics as string
     */
    String getCacheStats();

    /**
     * Warm up the cache with frequently accessed data
     */
    void warmUpCache();
}