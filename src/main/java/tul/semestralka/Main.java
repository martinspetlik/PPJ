package tul.semestralka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import tul.semestralka.api.service.DownloadWeatherService;

@SpringBootApplication
@EnableScheduling
public class Main {
    
    @Bean
    @ConditionalOnProperty(value = "readOnlyMode", matchIfMissing=true, havingValue="false")
    public DownloadWeatherService scheduledJob() {
        return new DownloadWeatherService();
    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext context = app.run(args);

    }
}
