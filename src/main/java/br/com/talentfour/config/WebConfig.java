package br.com.talentfour.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
				.allowedHeaders("*");
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			builder.simpleDateFormat(DATE_TIME_FORMAT);
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
		};
	}

}
