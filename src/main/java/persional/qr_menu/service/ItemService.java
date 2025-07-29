package persional.qr_menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import persional.qr_menu.dto.CreateItemRequest;
import persional.qr_menu.dto.MenuItemDto;
import persional.qr_menu.dto.UpdateItemRequest;
import persional.qr_menu.model.MenuItem;
import persional.qr_menu.repository.MenuItemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    
    private final MenuItemRepository menuItemRepository;
    
    public MenuItemDto createItem(CreateItemRequest request) {
        MenuItem item = MenuItem.builder()
                .name(request.getName())
                .size(request.getSize())
                .price(request.getPrice())
                .image(request.getImage())
                .category(request.getCategory())
                .description(request.getDescription())
                .isActive(true)
                .build();
        
        MenuItem savedItem = menuItemRepository.save(item);
        log.info("Created new menu item: {}", savedItem.getName());
        
        return convertToDto(savedItem);
    }
    
    public MenuItemDto updateItem(Long id, UpdateItemRequest request) {
        Optional<MenuItem> optionalItem = menuItemRepository.findById(id);
        
        if (optionalItem.isEmpty()) {
            throw new RuntimeException("Menu item not found with id: " + id);
        }
        
        MenuItem item = optionalItem.get();
        
        if (request.getName() != null) {
            item.setName(request.getName());
        }
        if (request.getSize() != null) {
            item.setSize(request.getSize());
        }
        if (request.getPrice() != null) {
            item.setPrice(request.getPrice());
        }
        if (request.getImage() != null) {
            item.setImage(request.getImage());
        }
        if (request.getCategory() != null) {
            item.setCategory(request.getCategory());
        }
        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }
        
        MenuItem savedItem = menuItemRepository.save(item);
        log.info("Updated menu item: {}", savedItem.getName());
        
        return convertToDto(savedItem);
    }
    
    public void deleteItem(Long id) {
        Optional<MenuItem> optionalItem = menuItemRepository.findById(id);
        
        if (optionalItem.isEmpty()) {
            throw new RuntimeException("Menu item not found with id: " + id);
        }
        
        MenuItem item = optionalItem.get();
        item.setIsActive(false); // Soft delete
        menuItemRepository.save(item);
        
        log.info("Soft deleted menu item: {}", item.getName());
    }
    
    private MenuItemDto convertToDto(MenuItem item) {
        return MenuItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .size(item.getSize())
                .price(item.getPrice())
                .image(item.getImage())
                .category(item.getCategory())
                .description(item.getDescription())
                .isActive(item.getIsActive())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}
