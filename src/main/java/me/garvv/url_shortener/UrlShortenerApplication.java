package me.garvv.url_shortener;

import me.garvv.url_shortener.Repository.UrlRepository;
import me.garvv.url_shortener.Service.UrlService;
import me.garvv.url_shortener.Utils.Base62;
import me.garvv.url_shortener.Utils.SecureRandomNumberGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class UrlShortenerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(UrlShortenerApplication.class, args);
        Optional<String> opt = Optional.of("hello");
        String str = "hello";
        System.out.println((Optional.of(str)));
	}
}
