package tul.semestralka.restapi;


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
import tul.semestralka.service.CountryService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CountryRestTest extends RestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;


    private Country country1 = new Country("Czech Republic", "cz");
    private Country country2 = new Country("Slovakia", "sk");
    private Country country3 = new Country("Hungary");
    private Country country4 = new Country("Poland");

    @Test
    public void testGetCountries() throws Exception {
        List<Country> countries = Arrays.asList(country1, country2);
        given(countryService.getAllCountries()).willReturn(countries);

        mockMvc.perform(get("/api/countries"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'title': 'Czech Republic', 'code': 'cz'}," +
                                                     " {'title': 'Slovakia', 'code': 'sk'}]"));
    }

    @Test
    public void testGetCountry() throws Exception {

        given(countryService.getCountry("cz")).willReturn(country1);

        mockMvc.perform(get("/api/country/cz"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'title': 'Czech Republic','code': 'cz'}"));

    }

    @Test
    public void testCreateCountry() throws Exception {
        given(countryService.exists(country2)).willReturn(true);
        mockMvc.perform(post("/api/country", country2))
                .andExpect(status().isBadRequest());


        given(countryService.exists(country4)).willReturn(false);
        given(countryService.validData(country4)).willReturn(false);
        mockMvc.perform(post("/api/country", country4))
                .andExpect(status().isBadRequest());


        given(countryService.exists(country1)).willReturn(false).willReturn(true);
        given(countryService.validData(country1)).willReturn(true);

        String requestJson=objectToJson(country1);

        mockMvc.perform(post("/api/country/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{'title': 'Czech Republic', 'code': 'cz'}"));
    }


    @Test
    public void testDeleteCountry() throws Exception {
        given(countryService.getCountry(country2.getCode())).willReturn(country2);
        given(countryService.exists(country2)).willReturn(true).willReturn(false);

        mockMvc.perform(delete("/api/country/{code}", country2.getCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        // Test country with town
        Town town = new Town("Prague", country1);
        given(countryService.getCountry(country1.getCode())).willReturn(country1);
        mockMvc.perform(delete("/api/country/{code}", country1.getCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testUpdateCountry() throws Exception {
        given(countryService.getCountry(country3.getCode())).willReturn(country3);
        String requestJson=objectToJson(country3);
        mockMvc.perform(put("/api/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

}
