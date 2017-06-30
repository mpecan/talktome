package org.ibayer.personal.talktome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Chat application spring.
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableCaching
public class TalkApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TalkApplication.class, args);
	}
	
}
