package hexlet.code.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
public final class UrlCheck {
    private Long id;
    private Long urlId;
    private int statusCode;
    private String h1;
    private String title;
    private String description;
    private Timestamp createdAt;
}
