package me.garvv.url_shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
//        Optional<String> opt = Optional.of("hello");
//        String str = "hello";
//        System.out.println((Optional.of(str)));
	}
}
