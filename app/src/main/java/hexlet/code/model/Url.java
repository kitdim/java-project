package hexlet.code.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "urls")
@EntityListeners(AuditingEntityListener.class)
@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Setter
@Getter
public final class Url {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "url no be empty")
    @ToString.Include
    private String name;
    @CreatedDate
    private Timestamp createdAt;
//    @OneToMany(mappedBy = "url", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UrlCheck> urlChecks = new ArrayList<>();
}
