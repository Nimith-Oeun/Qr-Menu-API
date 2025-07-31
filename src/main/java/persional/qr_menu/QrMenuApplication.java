package persional.qr_menu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@Controller
public class QrMenuApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrMenuApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QrMenuApplication.class);
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "QR_MENU API");
        String project = "QR_MENU (V1.0.0)";
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss 'GMT' xxx yyyy"));
        model.addAttribute("project", project);
        model.addAttribute("dateTime", dateTime);
        return "index";
    }

}
