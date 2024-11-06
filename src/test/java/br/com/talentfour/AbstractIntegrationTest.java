package br.com.talentfour;

import br.com.talentfour.extensions.ApplicationExtension;
import br.com.talentfour.extensions.PostgreSQLExtension;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.awaitility.core.ThrowingRunnable;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
@Tag("integration")
@AutoConfigureMockMvc
@ExtendWith({
        PostgreSQLExtension.class,
        ApplicationExtension.class
})
@ContextConfiguration(initializers = TestContainerConfiguration.EnvInitializer.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractIntegrationTest {

    private static final long TEST_TIMEOUT = 5_000L;

    private static final String HTTP = "http";
    private static final String LOCALHOST = "localhost";

    public static HttpResponse<String> requestBy(
            final String path,
            final String method,
            List<Map.Entry<String, String>> headers,
            List<Map.Entry<String, String>> queryParams,
            String body
    ) throws IOException, InterruptedException {

        var uriBuilder = UriComponentsBuilder.newInstance()
                .scheme(HTTP)
                .host(LOCALHOST)
                .port(System.getProperty("server.port"))
                .path(path);

        // Adicionando query parameters à URL, se existirem
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, String> queryParam : queryParams) {
                uriBuilder.queryParam(queryParam.getKey(), queryParam.getValue());
            }
        }

        var uri = uriBuilder.build().toUri();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri);

        // Adicionando os headers, se existirem
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers) {
                requestBuilder.header(header.getKey(), header.getValue());
            }
        }

        switch (method.toUpperCase()) {
            case "POST":
                requestBuilder.POST(body != null ?
                        HttpRequest.BodyPublishers.ofString(body) :
                        HttpRequest.BodyPublishers.noBody());
                break;
            case "PUT":
                requestBuilder.PUT(body != null ?
                        HttpRequest.BodyPublishers.ofString(body) :
                        HttpRequest.BodyPublishers.noBody());
                break;
            case "PATCH":
                requestBuilder.method("PATCH", body != null ?
                        HttpRequest.BodyPublishers.ofString(body) :
                        HttpRequest.BodyPublishers.noBody());
                break;
            case "DELETE":
                requestBuilder.DELETE();
                break;
            case "GET":
            default:
                requestBuilder.GET();
                break;
        }

        HttpRequest request = requestBuilder.build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected final <T> void awaitAndAssert(final Callable<T> callable, final Matcher<T> matcher) {
        Awaitility.await()
                .ignoreExceptions()
                .atMost(TEST_TIMEOUT, TimeUnit.MILLISECONDS)
                .until(callable, matcher);
    }

    protected final void awaitAndAssert(final ThrowingRunnable throwingRunnable) {
        Awaitility.await()
                .ignoreExceptions()
                .atMost(TEST_TIMEOUT, TimeUnit.MILLISECONDS)
                .untilAsserted(throwingRunnable);
    }

    protected final void await() {
        Awaitility.await()
                .ignoreExceptions()
                .atMost(TEST_TIMEOUT, TimeUnit.MILLISECONDS);
    }
}

