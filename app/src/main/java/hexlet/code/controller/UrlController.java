package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;

    @GetMapping(path = "/urls")
    public ResponseEntity<List<Url>> getAll() {
       List<Url> urls = urlService.getAll();
       return ResponseEntity.ok()
               .header("X-Total-Count", String.valueOf(urls.size()))
               .body(urls);
    }
    @PostMapping(path = "/urls")
    @ResponseStatus(HttpStatus.CREATED)
    public Url create(@RequestBody Url url) {
        return urlService.save(url);
    }
}
