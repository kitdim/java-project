package hexlet.code.service;

import hexlet.code.repository.UrlsRepository;
import hexlet.code.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlsRepository urlsRepository;

    public List<Url> getAll() {
        return urlsRepository.findAll();
    }
    public Url save(Url data) {
        return urlsRepository.save(data);
    }
}
