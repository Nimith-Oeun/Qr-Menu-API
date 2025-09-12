package persional.qr_menu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "menu")
    public List<MenuItemDto> getAllActiveItems() {
        log.info("Fetching all active menu items");
        List<MenuItem> items = menuItemRepository.findByIsActiveTrue();
        return items.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "menuByCategory", key = "#category")
    public List<MenuItemDto> getItemsByCategory(CategoryType category) {
        log.info("Fetching menu items by category: {}", category);
        List<MenuItem> items = menuItemRepository.findByCategoryAndIsActiveTrue(category);
        return items.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "menuSeparated")
    public MenuResponseDto getMenuSeparated() {
        log.info("Fetching menu items separated by category");
        List<MenuItemDto> drinks = getItemsByCategory(CategoryType.DRINK);
        List<MenuItemDto> foods = getItemsByCategory(CategoryType.FOOD);
        List<MenuItemDto> foodSets = getItemsByCategory(CategoryType.FOOD_SET);
        
        return MenuResponseDto.builder()
                .drinks(drinks)
                .foods(foods)
                .foodSets(foodSets)
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
