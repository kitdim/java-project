package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.sql.Timestamp;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public class AppTest {
    Javalin app;

    @BeforeEach
    public final void setup() throws SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
        });
    }

    @Test
    public void testShowUrls() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
        }));
    }

    @Test
    public void testCreateUrl() throws SQLException {
        var url = Url.builder()
                .id(1L)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .name("https://www.example1.com")
                .build();
        UrlRepository.save(url);

        JavalinTest.test(app, ((server, client) -> {
            var responseBody = "url=https://www.example2.com";
            var response = client.post(NamedRoutes.urlsPath(), responseBody);

            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            assertThat(UrlRepository.getEntities().size()).isEqualTo(2);
            assertTrue(UrlRepository.findByName("https://www.example1.com").isPresent());
            assertTrue(UrlRepository.find(1L).isPresent());

            response = client.post(NamedRoutes.urlsPath(), responseBody);
            assertThat(UrlRepository.getEntities().size()).isEqualTo(2);
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());

            responseBody = "url=httpdssds31s://www.example2.com";
            response = client.post(NamedRoutes.urlsPath(), responseBody);
            assertTrue(UrlRepository.findByName("httpdssds31s://www.example2.com").isEmpty());
        }));
    }

    @Test
    public void testShowUrl() throws SQLException {
        var url = Url.builder()
                .id(1L)
                .name("https://www.example1.com")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
        UrlRepository.save(url);
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(HttpStatus.FOUND.getCode());
            assertTrue(response.body().string().contains(url.getName()));
            response = client.get(NamedRoutes.urlPath(99L));
            assertThat(response.code()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
        }));
    }
}
