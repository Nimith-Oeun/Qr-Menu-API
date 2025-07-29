package persional.qr_menu.dto;

import persional.qr_menu.enums.CategoryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 50, message = "Size must not exceed 50 characters")
    private String size;

    @Size(max = 20, message = "Price must not exceed 20 characters")
    private String price;

    private String image;

    private CategoryType category;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
}
