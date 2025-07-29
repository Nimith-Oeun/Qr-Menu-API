package persional.qr_menu.service;

import persional.qr_menu.dto.CreateItemRequest;
import persional.qr_menu.dto.UpdateItemRequest;

/**
 * Service interface for business validation
 * Handles complex validation rules beyond basic field validation
 */
public interface ValidationService {

    /**
     * Validate create item request with business rules
     * @param request The request to validate
     * @throws RuntimeException if validation fails
     */
    void validateCreateRequest(CreateItemRequest request);

    /**
     * Validate update item request with business rules
     * @param id The ID of the item being updated
     * @param request The request to validate
     * @throws RuntimeException if validation fails
     */
    void validateUpdateRequest(Long id, UpdateItemRequest request);

    /**
     * Validate menu item name for uniqueness and format
     * @param name The name to validate
     * @param excludeId ID to exclude from uniqueness check (for updates)
     * @return true if valid, false otherwise
     */
    boolean isValidItemName(String name, Long excludeId);

    /**
     * Validate price format
     * @param price The price string to validate
     * @return true if valid format, false otherwise
     */
    boolean isValidPriceFormat(String price);

    /**
     * Validate image URL format
     * @param imageUrl The image URL to validate
     * @return true if valid URL format, false otherwise
     */
    boolean isValidImageUrl(String imageUrl);
}