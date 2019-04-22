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
import tul.semestralka.service.TownService;
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
public class TownRestTest extends RestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TownService townService;

    @MockBean
    private CountryService countryService;

    private Country country1 = new Country("Czech Republic", "cz");
    private Country country2 = new Country("Germany", "de");

    private Town town1 = new Town("Prague", country1);
    private Town town2 = new Town("Berlin", country2);
    private Town town3 = new Town("Bonn", country2);
    private Town town4 = new Town("Ostrava", country1);

    @Test
    public void testGetTowns() throws Exception {
        List<Town> towns = Arrays.asList(town1, town2);
        given(townService.getTowns()).willReturn(towns);

        String responseJson=objectToJson(towns);

        mockMvc.perform(get("/api/towns"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));
    }

    @Test
    public void testGetTown() throws Exception {
        given(townService.getTown(town1.getId())).willReturn(town1);

        String responseJson=objectToJson(town1);

        mockMvc.perform(get("/api/town/{id}", town1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        mockMvc.perform(get("/api/town/{id}", -1))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testCreateTown() throws Exception {
        given(townService.exists(town2)).willReturn(true);
        mockMvc.perform(post("/api/town", town2))
                .andExpect(status().isBadRequest());

        given(townService.exists(town4)).willReturn(false);
        mockMvc.perform(post("/api/town", town4))
                .andExpect(status().isBadRequest());


        given(townService.exists(town1)).willReturn(false).willReturn(true);
        given(countryService.exists(town1.getCountry())).willReturn(true);

        String requestJson=objectToJson(town1);

        mockMvc.perform(post("/api/town")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(requestJson));
    }


    @Test
    public void testDeleteTown() throws Exception {
        given(townService.getTown(town2.getId())).willReturn(town2);
        given(townService.exists(town2)).willReturn(true);

        mockMvc.perform(delete("/api/town/{townId}", town2.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        // Test country with town
        given(townService.getTown(town3.getId())).willReturn(town3);
        mockMvc.perform(delete("/api/town/{townId}", -1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testUpdateTown() throws Exception{
        given(townService.getTown(town1.getId())).willReturn(null);
        String requestJson=objectToJson(town1);
        mockMvc.perform(put("/api/town")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());

        given(townService.getTown(town1.getId())).willReturn(town1);
        String requestJson2=objectToJson(town1);
        mockMvc.perform(put("/api/town")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson2))
                .andExpect(status().isOk());
    }
}
