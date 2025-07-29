package persional.qr_menu.dto;

import persional.qr_menu.enums.CategoryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Size is required")
    @Size(max = 50, message = "Size must not exceed 50 characters")
    private String size;

    @NotBlank(message = "Price is required")
    @Size(max = 20, message = "Price must not exceed 20 characters")
    private String price;

    private String image;

    @NotNull(message = "Category is required")
    private CategoryType category;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
}
