package tul.semestralka.restapi;


import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tul.semestralka.data.Country;
import tul.semestralka.data.Town;
import tul.semestralka.data.Weather;
import tul.semestralka.data.WeatherAverage;
import tul.semestralka.service.MongoWeatherService;
import tul.semestralka.service.TownService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WeatherRestTest extends RestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public MongoWeatherService weatherService;

    @MockBean
    public TownService townService;

    private ZonedDateTime t1 = ZonedDateTime.now();
    private ZonedDateTime t2 = ZonedDateTime.now();
    private ZonedDateTime t3 = ZonedDateTime.now();
    private ZonedDateTime t4 = ZonedDateTime.now();

    private Weather weather1 = new Weather(1, (float)80.5, (float)1050, (float)57, (float)10, (float)25.6, t1);
    private Weather weather2 = new Weather(2, (float)85.5, (float)950, (float)59, (float)8, (float)180.1, t2);
    private Weather weather3 = new Weather(3, (float)30.7, (float)1108, (float)75, (float)15.5, (float)125.6, t3);
    private Weather weather4 = new Weather(4, (float)12.75, (float)1250, (float)85, (float)7.8, (float)180, t4);


    @Test
    public void testGetWeathers() throws Exception {
        List<Weather> weathers = Arrays.asList(weather1, weather2);
        given(weatherService.getAll()).willReturn(weathers);

        String response = objectToJson(weathers);

        mockMvc.perform(get("/api/weathers"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }


    @Test
    public void testGetWeather() throws Exception {
        weather1.setId(new ObjectId());
        weather2.setId(new ObjectId());

        given(weatherService.find(weather1.getId())).willReturn(weather1);

        String response = objectToJson(weather1);
        mockMvc.perform(get("/api/weather/{id}", weather1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        mockMvc.perform(get("/api/weather/{id}", 123))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testCreateWeather() throws Exception {
        given(weatherService.exists(weather1)).willReturn(true);
        mockMvc.perform(post("/api/weather", weather1))
                .andExpect(status().isBadRequest());

        weather2.setId(new ObjectId());
        String request=objectToJson(weather2);
        given(weatherService.exists(any())).willReturn(true);
        mockMvc.perform(post("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest());

        weather4.setId(new ObjectId());
        String request1=objectToJson(weather4);
        given(weatherService.exists(any())).willReturn(false);
        given(townService.getTown(any())).willReturn(null);
        mockMvc.perform(post("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request1))
                .andExpect(status().isBadRequest());

        weather3.setId(new ObjectId());
        given(weatherService.exists(any())).willReturn(false);
        Country c = new Country("test", "test");
        Town t = new Town("test ", c);
        given(townService.getTown(any())).willReturn(t);
        String request2=objectToJson(weather3);
        mockMvc.perform(post("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request2))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteWeather() throws Exception {
        weather2.setId(new ObjectId());
        given(weatherService.find(weather2.getId())).willReturn(weather2);
        given(weatherService.exists(weather2)).willReturn(true).willReturn(false);
        mockMvc.perform(delete("/api/weather/{id}", weather2.getId()))
                .andExpect(status().isOk());

        given(weatherService.find(weather3.getId())).willReturn(null);
        given(weatherService.exists(weather3)).willReturn(false);
        mockMvc.perform(delete("/api/weather/{id}", weather2.getId()))
                .andExpect(status().isBadRequest());

        weather4.setId(new ObjectId());
        given(weatherService.find(weather4.getId())).willReturn(weather4);
        given(weatherService.exists(weather4)).willReturn(true).willReturn(true);
        mockMvc.perform(delete("/api/weather/{id}", weather4.getId()))
                .andExpect(status().isInternalServerError());

    }


    @Test
    public void testUpdateWeather() throws Exception{
        given(townService.getTown(any())).willReturn(null);
        String requestJson=objectToJson(weather1);
        mockMvc.perform(put("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());


        //weather2.setId(new ObjectId());
        Country c = new Country("test", "test");
        Town t = new Town("test ", c);
        given(townService.getTown(any())).willReturn(t);
        given(weatherService.find(any())).willReturn(weather2);
        String requestJson2=objectToJson(weather2);
        mockMvc.perform(put("/api/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson2))
                .andExpect(status().isOk());
    }

    @Test
    public void testActualWeather() throws Exception{
        Integer townId = 10;
        given(weatherService.existsByTownId(townId)).willReturn(false);

        mockMvc.perform(get("/api/actual-weather/{townId}", townId))
                .andExpect(status().isBadRequest());

        given(weatherService.existsByTownId(townId)).willReturn(true);
        given(weatherService.getActual(townId)).willReturn(weather2);
        given(weatherService.exists(weather2)).willReturn(true);
        String response = objectToJson(weather2);
        mockMvc.perform(get("/api/actual-weather/{townId}", townId))
                .andExpect(status().isOk()).andExpect(content().json(response));

    }

    @Test
    public void testAverageWeather() throws Exception{
        Integer townId = 12;
        String period = "day";
        given(weatherService.existsByTownId(townId)).willReturn(false);
        mockMvc.perform(get("/api/average-weather/{townId}", townId)
                .param("period", period))
                .andExpect(status().isBadRequest());

        WeatherAverage weatherAvg = new WeatherAverage(80, 100, (float)85.7, (float)12.6, (float)80.2);
        given(weatherService.existsByTownId(townId)).willReturn(true);
        given(weatherService.getAverage(townId, period)).willReturn(weatherAvg);
        String response = objectToJson(weatherAvg);
        mockMvc.perform(get("/api/average-weather/{townId}", townId)
                .param("period", period))
                .andExpect(status().isOk()).andExpect(content().json(response));

        given(weatherService.existsByTownId(townId)).willReturn(true);
        given(weatherService.getAverage(townId, period)).willReturn(null);
        mockMvc.perform(get("/api/average-weather/{townId}", townId)
                .param("period", period))
                .andExpect(status().isNotFound());
    }
}
