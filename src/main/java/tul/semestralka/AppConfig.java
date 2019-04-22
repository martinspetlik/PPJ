package tul.semestralka;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        int timeout = 5000;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeout);
        factory.setReadTimeout(timeout);


        return new RestTemplate(factory);
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper obm = new ObjectMapper();
//        obm.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);;
//
//        return obm;
//    }
}

