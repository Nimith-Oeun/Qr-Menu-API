package persional.qr_menu.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import persional.qr_menu.dto.ApiResponse;

public class ResponseUtil {
    
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }
    
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.success(data, message));
    }
    
    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(message));
    }
}
