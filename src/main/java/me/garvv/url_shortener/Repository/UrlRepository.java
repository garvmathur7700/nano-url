package me.garvv.url_shortener.Repository;

import me.garvv.url_shortener.Model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    public String findByShortUrl(String shortUrl);
}
