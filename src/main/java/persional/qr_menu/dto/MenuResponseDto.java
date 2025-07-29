package persional.qr_menu.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {
    private List<MenuItemDto> drinks;
    private List<MenuItemDto> foods;
}
