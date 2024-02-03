package hexlet.code.util;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Normalizer {
    public static String getNormalizedURL(URL parsedUrl) {
        return String
                .format(
                        "%s://%s%s",
                        parsedUrl.getProtocol(),
                        parsedUrl.getHost(),
                        parsedUrl.getPort() == -1 ? "" : ":" + parsedUrl.getPort()
                )
                .toLowerCase();
    }

    public static String getNormalizedTime(Timestamp time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return time.toLocalDateTime().format(formatter);
    }
}
