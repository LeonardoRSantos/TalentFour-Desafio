package br.com.talentfour.fixture;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class TestUtils {

    private static final String PATTERN = "\\Z";

    public static String getContentFromInputStream(InputStream jsonForPostFileToEvaluation) {
        return new Scanner(Objects.requireNonNull(jsonForPostFileToEvaluation), StandardCharsets.UTF_8).useDelimiter(PATTERN).next();
    }
}
