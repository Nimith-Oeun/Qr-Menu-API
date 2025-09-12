package persional.qr_menu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import persional.qr_menu.dto.ApiResponse;
import persional.qr_menu.dto.CreateItemRequest;
import persional.qr_menu.dto.MenuItemDto;
import persional.qr_menu.dto.UpdateItemRequest;
import persional.qr_menu.service.ItemService;
import persional.qr_menu.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemService itemService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<MenuItemDto>> createItem(
            @RequestBody @Valid CreateItemRequest request) {
        MenuItemDto item = itemService.createItem(request);
        return ResponseUtil.success(item, "Item created successfully", HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItemDto>> updateItem(
            @PathVariable Long id, 
            @RequestBody @Valid UpdateItemRequest request) {
        MenuItemDto item = itemService.updateItem(id, request);
        return ResponseUtil.success(item, "Item updated successfully");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseUtil.success(null, "Item deleted successfully");
    }
}