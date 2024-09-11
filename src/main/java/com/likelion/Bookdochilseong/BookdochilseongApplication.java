package com.likelion.Bookdochilseong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookdochilseongApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookdochilseongApplication.class, args);
	}

}
