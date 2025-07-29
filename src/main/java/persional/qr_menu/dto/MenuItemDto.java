package persional.qr_menu.dto;

import persional.qr_menu.enums.CategoryType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDto {
    private Long id;
    private String name;
    private String size;
    private String price;
    private String image;
    private CategoryType category;
    private String description;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
