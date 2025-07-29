package persional.qr_menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import persional.qr_menu.dto.MenuItemDto;
import persional.qr_menu.dto.MenuResponseDto;
import persional.qr_menu.enums.CategoryType;
import persional.qr_menu.model.MenuItem;
import persional.qr_menu.repository.MenuItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    
    private final MenuItemRepository menuItemRepository;
    
    public List<MenuItemDto> getAllActiveItems() {
        List<MenuItem> items = menuItemRepository.findByIsActiveTrue();
        return items.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<MenuItemDto> getItemsByCategory(CategoryType category) {
        List<MenuItem> items = menuItemRepository.findByCategoryAndIsActiveTrue(category);
        return items.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public MenuResponseDto getMenuSeparated() {
        List<MenuItemDto> drinks = getItemsByCategory(CategoryType.DRINK);
        List<MenuItemDto> foods = getItemsByCategory(CategoryType.FOOD);
        
        return MenuResponseDto.builder()
                .drinks(drinks)
                .foods(foods)
                .build();
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
