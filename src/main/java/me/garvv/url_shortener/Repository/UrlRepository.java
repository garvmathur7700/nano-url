package me.garvv.url_shortener.Repository;

import me.garvv.url_shortener.Model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query("SELECT u.shortUrl FROM Url u WHERE u.longUrl = :longUrl")
    String findShortUrlByLongUrl(@Param("longUrl") String longUrl);
    Boolean existsByLongUrl(String longUrl);
}
