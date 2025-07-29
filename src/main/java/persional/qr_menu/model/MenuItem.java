package persional.qr_menu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import persional.qr_menu.enums.CategoryType;

@Entity
@Table(name = "menu_items")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String size;

    @NotBlank
    @Size(max = 20)
    private String price;

    private String image;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CategoryType category;

    @Size(max = 1000)
    private String description;

    @Builder.Default
    private Boolean isActive = true;
}