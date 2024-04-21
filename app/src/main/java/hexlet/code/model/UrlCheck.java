//package hexlet.code.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//
//import java.sql.Timestamp;
//
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Entity
//@Table(name = "urls-checks")
//@Getter
//public final class UrlCheck {
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    private Long id;
//    @ManyToOne
//    private Url url;
//    private int statusCode;
//    private String h1;
//    private String title;
//    private String description;
//    private Timestamp createdAt;
//}
