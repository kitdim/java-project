package hexlet.code.repository;

import hexlet.code.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlsRepository extends JpaRepository<Url, Long> {
}