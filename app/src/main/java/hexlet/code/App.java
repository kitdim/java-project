package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.RootController;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
public class App {
    private static final String PORT_DEFAULT = "7070";
    private static final String JDBC_URL_DEFAULT = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
    public static void main(String[] args) throws SQLException {
        log.info("Start app " + Timestamp.valueOf(LocalDateTime.now()));
        Javalin app = getApp();
        int port = getPort();
        app.start(port);
    }

    public static Javalin getApp() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        setData(hikariConfig);
        DataSource dataSource = new HikariDataSource(hikariConfig);

        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("schema.sql");

        if (inputStream == null) {
            throw new RuntimeException("There is no file for creating the database.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String sql = reader.lines().collect(Collectors.joining("\n"));

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        Javalin app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        app.get(NamedRoutes.rootPath(), RootController::index);

        return app;
    }

    private static void setData(HikariConfig hikariConfig) {
        log.info("****Database start connection****");
        String jdbcUrl = System.getenv().getOrDefault("JDBC_URL_DEFAULT", JDBC_URL_DEFAULT);
        hikariConfig.setJdbcUrl(jdbcUrl);
        boolean isProd = System.getenv().getOrDefault("APP_ENV", "dev").equals("prod");

        if (isProd) {
            log.info("****Database connection to postgresql****");
            String username = System.getenv("JDBC_DATABASE_USERNAME");
            String password = System.getenv("JDBC_DATABASE_PASSWORD");
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
        }
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", PORT_DEFAULT);
        log.info("****Port:" + port + "****");
        return Integer.parseInt(port);
    }
}
