package conductor;

import conductor.service.DocumentService;
import conductor.service.DocumentServiceCacheable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DocumentService eventService() {
        return new DocumentServiceCacheable();
    }

}
