package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.StringJoiner;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsCheckRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.HttpStatus;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public final class AppTest {
    private static Javalin app;
    private static MockWebServer mockServer;
    private static String urlName;
    private static final String HTML_PATH = "src/test/resources/index.html";

    public static String getContentOfHtmlFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(HTML_PATH));
        String lineOfFile = reader.readLine();
        var result = new StringJoiner("\n");

        while (lineOfFile != null) {
            result.add(lineOfFile);
            lineOfFile = reader.readLine();
        }
        return result.toString();
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockServer = new MockWebServer();
        urlName = mockServer.url("/").toString();
        var mockResponse = new MockResponse().setBody(getContentOfHtmlFile());
        mockServer.enqueue(mockResponse);
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        app = App.getApp();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockServer.shutdown();
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
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .name("https://www.example1.com")
                .build();
        UrlsRepository.save(url);

        JavalinTest.test(app, ((server, client) -> {
            var responseBody = "url=https://www.example2.com";
            var response = client.post(NamedRoutes.urlsPath(), responseBody);

            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            assertThat(UrlsRepository.getEntities().size()).isEqualTo(2);
            assertTrue(UrlsRepository.findByName("https://www.example1.com").isPresent());
            assertTrue(UrlsRepository.find(1L).isPresent());

            response = client.post(NamedRoutes.urlsPath(), responseBody);
            assertThat(UrlsRepository.getEntities().size()).isEqualTo(2);
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());

            responseBody = "url=httpdssds31s://www.example2.com";
            response = client.post(NamedRoutes.urlsPath(), responseBody);
            assertTrue(UrlsRepository.findByName("httpdssds31s://www.example2.com").isEmpty());
        }));
    }

    @Test
    public void testShowUrl() throws SQLException {
        var url = Url.builder()
                .name("https://www.example1.com")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
        UrlsRepository.save(url);
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
            assertTrue(response.body().string().contains(url.getName()));
            response = client.get(NamedRoutes.urlPath(99L));
            assertThat(response.code()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
        }));
    }
    @Test
    public void testCheckUrl() throws SQLException {
        var url = Url.builder()
                .name(urlName)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
        UrlsRepository.save(url);

        JavalinTest.test(app, (server1, client) -> {
            var response = client.post(NamedRoutes.urlCheckPath(url.getId()));
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());

            var urlCheck = UrlsCheckRepository.find(url.getId()).orElse(null);
            var title = urlCheck.getTitle();
            var h1 = urlCheck.getH1();
            var description = urlCheck.getDescription();

            assertThat(title).isEqualTo("This is a title");
            assertThat(h1).isEqualTo("This is a header");
            assertThat(description).isEqualTo("This is a description");
        });
    }
    @Test
    void testStore() throws SQLException {
        String inputUrl = "https://ru.hexlet.io";

        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=" + inputUrl;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
        });

        Url actualUrl = UrlsRepository.findByName(inputUrl).orElse(null);

        assertThat(actualUrl).isNotNull();
        assertThat(actualUrl.getName()).isEqualTo(inputUrl);
    }
}
