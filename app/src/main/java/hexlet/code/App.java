package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
public class App {
    private static final String PORT_DEFAULT = "7070";
    private static final String JDBC_URL_DEFAULT = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
    public static void main(String[] args) {
        log.info("Start app " + Timestamp.valueOf(LocalDateTime.now()));
        Javalin app = getApp();
        int port = getPort();
        app.start(port);
    }

    public static Javalin getApp() {
        Javalin app = Javalin.create(config -> {
            config.plugins.enableDevLogging();
        });
        app.get(NamedRoutes.rootPath(), RootController::index);

        return app;
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", PORT_DEFAULT);
        log.info("Port:" + port);
        return Integer.parseInt(port);
    }
}
