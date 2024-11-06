package br.com.talentfour;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

@Component
public class TestContainerConfiguration {

    public static final String TALENTFOUR_DB = "talentfour";
    public static final String DB_USER = "talentfour";
    public static final String DB_PASSWORD = "talentfour";
    public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13-alpine")
            .withDatabaseName(TALENTFOUR_DB)
            .withUsername(DB_USER)
            .withPassword(DB_PASSWORD);

    static {
        POSTGRES.start();
    }

    static class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            configurePostgresEnvironment();
        }

    }

    public static void configurePostgresEnvironment() {
        System.setProperty("spring.datasource.url",
                String.format("jdbc:postgresql://localhost:%d/%s",
                        POSTGRES.getFirstMappedPort(), TALENTFOUR_DB));
        System.setProperty("spring.datasource.username", DB_USER);
        System.setProperty("spring.datasource.password", DB_PASSWORD);
    }
}
