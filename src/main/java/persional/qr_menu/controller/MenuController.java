package persional.qr_menu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import persional.qr_menu.dto.ApiResponse;
import persional.qr_menu.dto.MenuItemDto;
import persional.qr_menu.dto.MenuResponseDto;
import persional.qr_menu.enums.CategoryType;
import persional.qr_menu.service.MenuService;
import persional.qr_menu.util.ResponseUtil;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Validated
@Slf4j
@RequiredArgsConstructor
public class MenuController {
    
    private final MenuService menuService;
    
    @GetMapping("/menu")
    @Cacheable("menu")
    public ResponseEntity<ApiResponse<List<MenuItemDto>>> getAllMenuItems() {
        List<MenuItemDto> items = menuService.getAllActiveItems();
        return ResponseUtil.success(items, "Menu items retrieved successfully");
    }
    
    @GetMapping("/menu/{category}")
    @Cacheable(value = "menuByCategory", key = "#category")
    public ResponseEntity<ApiResponse<List<MenuItemDto>>> getMenuByCategory(
            @PathVariable @Valid CategoryType category) {
        List<MenuItemDto> items = menuService.getItemsByCategory(category);
        return ResponseUtil.success(items, "Menu items retrieved successfully");
    }
    
    @GetMapping("/menu-separated")
    @Cacheable("separatedMenu")
    public ResponseEntity<ApiResponse<MenuResponseDto>> getMenuSeparated() {
        MenuResponseDto response = menuService.getMenuSeparated();
        return ResponseUtil.success(response, "Separated menu retrieved successfully");
    }
}