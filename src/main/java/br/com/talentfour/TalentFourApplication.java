package br.com.talentfour;

import br.com.talentfour.config.JwtConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfigProperties.class)
public class TalentFourApplication {

	@Value("${app.allowedOrigins}")
	private String allowedOrigins;

	public static void main(String[] args) {
		SpringApplication.run(TalentFourApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Fortaleza"));
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins(allowedOrigins)
						.allowedMethods("POST", "PUT", "GET", "DELETE", "OPTIONS", "PATCH")
						.allowedHeaders("*");
			}
		};
	}
}
