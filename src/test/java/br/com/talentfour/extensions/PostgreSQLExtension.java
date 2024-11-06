package br.com.talentfour.extensions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostgreSQLExtension implements AfterEachCallback {

    private final static List<String> TABLES_IGNORE = List.of(
            "flyway_schema_history",
            "holidays",
            "profile"
    );

    static {
        var tablesToIgnore = TABLES_IGNORE.stream().collect(Collectors.joining("','", "'", "'"));
        SELECT_ALL_TABLES = """
                SELECT table_name
                FROM information_schema.tables
                WHERE table_schema = 'public'
                AND table_name NOT IN (""" + tablesToIgnore +
                """
                        )
                        ORDER BY table_name
                        """;

        SELECT_ALL_SEQUENCES = """
                SELECT sequence_name
                FROM information_schema.sequences
                WHERE sequence_schema = 'public'
                """;
    }

    private final static String SELECT_ALL_TABLES;
    private final static String SELECT_ALL_SEQUENCES;

    private final static String TRUNCATE = "TRUNCATE $1 CASCADE";
    private final static String RESET_SEQUENCE = "ALTER SEQUENCE $1 RESTART WITH 1";

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        cleaningDatabase(extensionContext);
    }

    private void cleaningDatabase(ExtensionContext extensionContext) {
        log.info("Clearing database before the next test.");
        var jdbcTemplate = SpringExtension.getApplicationContext(extensionContext).getBean(JdbcTemplate.class);

        // Truncate all tables
        var tables = jdbcTemplate.queryForList(SELECT_ALL_TABLES, String.class);
        tables.forEach(table -> jdbcTemplate.execute(TRUNCATE.replace("$1", table)));

        // Reset all sequences
        var sequences = jdbcTemplate.queryForList(SELECT_ALL_SEQUENCES, String.class);
        sequences.forEach(sequence -> jdbcTemplate.execute(RESET_SEQUENCE.replace("$1", sequence)));
    }
}
