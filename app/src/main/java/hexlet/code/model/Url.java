package hexlet.code.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
public final class Url {
    private Long id;
    private String name;
    private Timestamp createdAt;
}
