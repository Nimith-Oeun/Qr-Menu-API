package persional.qr_menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import persional.qr_menu.enums.CategoryType;
import persional.qr_menu.model.MenuItem;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    
    List<MenuItem> findByCategoryAndIsActiveTrue(CategoryType category);
    
    List<MenuItem> findByIsActiveTrue();
    
    @Query("SELECT m FROM MenuItem m WHERE m.isActive = true ORDER BY m.category, m.name")
    List<MenuItem> findAllActiveOrderByCategoryAndName();
}
