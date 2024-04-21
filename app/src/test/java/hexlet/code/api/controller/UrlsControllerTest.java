package hexlet.code.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlsControllerTest {
    @Autowired
    private UrlsRepository urlsRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test find all")
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/urls"))
                .andExpect(status().isOk())
                .andReturn();
        String body = result.getResponse().getContentAsString();
        List<Url> urls = objectMapper.readValue(body, new TypeReference<List<Url>>() {
        });
        List<Url> expectedUrls = urlsRepository.findAll();

        assertThat(urls).containsAll(expectedUrls);
    }
    @Test
    @DisplayName("Test create url")
    public void testCreate() throws Exception {
        Url someUrl = new Url();
        someUrl.setId(1L);
        someUrl.setName("test");
        MockHttpServletRequestBuilder request = post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(someUrl));
        mockMvc.perform(request)
                .andExpect(status().isCreated());
        Url findUrl = urlsRepository.findById(someUrl.getId()).get();
        assertNotNull(findUrl);
        assertThat(findUrl.getName()).isEqualTo(someUrl.getName());

        urlsRepository.deleteAll();
    }
}
