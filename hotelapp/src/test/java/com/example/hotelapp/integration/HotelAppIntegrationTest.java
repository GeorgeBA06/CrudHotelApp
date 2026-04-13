package com.example.hotelapp.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class HotelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String validCreateHotelRequest() {
        return """
            {
              "name": "Hilton Minsk",
              "description": "Luxury hotel in Minsk",
              "brand": "Hilton",
              "addressRequestDto": {
                "houseNumber": 9,
                "street": "Pobediteley Avenue",
                "city": "Minsk",
                "country": "Belarus",
                "postCode": "220004"
              },
              "contactsRequestDto": {
                "phone": "+375 17 309-80-00",
                "email": "minsk@hilton.com"
              },
              "arrivalTimeRequestDto": {
                "checkIn": "14:00",
                "checkOut": "12:00"
              }
            }
            """;
    }

    @Test
    @DisplayName("POST /hotels should create hotel")
    void shouldCreateHotel() throws Exception {
        String requestBody = validCreateHotelRequest();

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Hilton Minsk"))
                .andExpect(jsonPath("$.brand").doesNotExist())
                .andExpect(jsonPath("$.phone").value("+375 17 309-80-00"))
                .andExpect(jsonPath("$.address").value("9 Pobediteley Avenue, Minsk, 220004, Belarus"));
    }

    @Test
    @DisplayName("GET /hotels should return list")
    void shouldReturnHotelList() throws Exception {
        String requestBody = validCreateHotelRequest();

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hilton Minsk"));
    }

    @Test
    @DisplayName("GET /hotels/{id} should return 404 for missing hotel")
    void shouldReturn404WhenHotelNotFound() throws Exception {
        mockMvc.perform(get("/hotels/666"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Hotel not found"));
    }

    @Test
    @DisplayName("POST /hotels/{id}/amenities should add amenities")
    void shouldAddAmenitiesToHotel() throws Exception {
        String createHotelRequest = validCreateHotelRequest();

        String response = mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createHotelRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long hotelId = objectMapper.readTree(response).get("id").asLong();

        String amenitiesRequest = """
            [
              "Free parking",
              "Free WiFi",
              "Fitness center"
            ]
            """;

        mockMvc.perform(post("/hotels/{id}/amenities", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(amenitiesRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(hotelId))
                .andExpect(jsonPath("$.amenities.length()").value(3))
                .andExpect(jsonPath("$.amenities[0]").exists());
    }

    @Test
    @DisplayName("GET /search should filter hotels by city")
    void shouldSearchHotelsByCity() throws Exception {
        String requestBody = validCreateHotelRequest();

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(get("/search")
                        .param("city", "Minsk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").doesNotExist())
                .andExpect(jsonPath("$[0].name").value("Hilton Minsk"));
    }

    @Test
    @DisplayName("GET /histogram/brand should return grouped hotels by brand")
    void shouldReturnHistogramByBrand() throws Exception {
        String requestBody = validCreateHotelRequest();

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        mockMvc.perform(get("/histogram/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Hilton").value(1));
    }
}