package persional.qr_menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persional.qr_menu.dto.ApiResponse;
import persional.qr_menu.service.CacheService;
import persional.qr_menu.util.ResponseUtil;

/**
 * Controller for cache management operations
 */
@RestController
@RequestMapping("/api/v1/cache")
@CrossOrigin(origins = "*")
@Tag(name = "Cache Management", description = "Operations for managing Redis cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/clear/all")
    @Operation(summary = "Clear all menu caches", description = "Clears all menu-related caches from Redis")
    public ResponseEntity<ApiResponse<String>> clearAllMenuCaches() {
        try {
            cacheService.clearAllMenuCaches();
            return ResponseUtil.success("Success", "All menu caches cleared successfully");
        } catch (Exception e) {
            return ResponseUtil.error("Failed to clear caches: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/clear/category/{category}")
    @Operation(summary = "Clear category cache", description = "Clears cache for a specific category")
    public ResponseEntity<ApiResponse<String>> clearCategoryCache(
            @Parameter(description = "Category name to clear cache for")
            @PathVariable String category) {
        try {
            cacheService.clearCategoryCache(category);
            return ResponseUtil.success("Success", "Cache cleared for category: " + category);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to clear category cache: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/clear/pattern")
    @Operation(summary = "Clear cache by pattern", description = "Clears cache entries matching a specific pattern")
    public ResponseEntity<ApiResponse<String>> clearCacheByPattern(
            @Parameter(description = "Cache key pattern to match (e.g., 'menu:*')")
            @RequestParam String pattern) {
        try {
            cacheService.clearCacheByPattern(pattern);
            return ResponseUtil.success("Success", "Cache cleared for pattern: " + pattern);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to clear cache by pattern: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/health")
    @Operation(summary = "Check cache health", description = "Checks if Redis cache is healthy and accessible")
    public ResponseEntity<ApiResponse<Boolean>> checkCacheHealth() {
        try {
            boolean isHealthy = cacheService.isCacheHealthy();
            String message = isHealthy ? "Cache is healthy and accessible" : "Cache is not accessible";
            HttpStatus status = isHealthy ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
            return ResponseEntity.status(status).body(ApiResponse.success(isHealthy, message));
        } catch (Exception e) {
            return ResponseUtil.error("Failed to check cache health: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stats")
    @Operation(summary = "Get cache statistics", description = "Returns Redis cache statistics and information")
    public ResponseEntity<ApiResponse<String>> getCacheStats() {
        try {
            String stats = cacheService.getCacheStats();
            return ResponseUtil.success(stats, "Cache statistics retrieved successfully");
        } catch (Exception e) {
            return ResponseUtil.error("Failed to get cache statistics: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/warmup")
    @Operation(summary = "Warm up cache", description = "Preloads frequently accessed data into cache")
    public ResponseEntity<ApiResponse<String>> warmUpCache() {
        try {
            cacheService.warmUpCache();
            return ResponseUtil.success("Success", "Cache warm-up initiated successfully");
        } catch (Exception e) {
            return ResponseUtil.error("Failed to warm up cache: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
