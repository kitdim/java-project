package hexlet.code.util;

import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Normalizer {
    public static String getNormalizedURL(URL url) {
        String protocol = url.getProtocol();
        String symbols = "://";
        String host = url.getHost();
        String port = url.getPort() == -1 ? "" : ":" + url.getPort();

        return protocol + symbols + host + port;
    }
    public static String getNormalizedTime(Timestamp time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return time.toLocalDateTime().format(formatter);
    }
}
