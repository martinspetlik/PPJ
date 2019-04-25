package tul.semestralka;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Main {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext context = app.run(args);

    }
}
