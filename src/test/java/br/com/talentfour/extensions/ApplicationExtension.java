package br.com.talentfour.extensions;

import br.com.talentfour.TalentFourApplication;
import org.junit.jupiter.api.extension.*;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;


@Component
public class ApplicationExtension implements BeforeAllCallback, AfterAllCallback,
        ParameterResolver, ApplicationListener<ApplicationReadyEvent> {

    private static Map<Class, ApplicationContext> testContexts = new HashMap<>();

    private ClientAndServer mockServer;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (mockServer != null) {
            return;
        }
        mockServer = ClientAndServer.startClientAndServer();
        System.setProperty("spring.profiles.active", "integration");
        System.setProperty("server.port", mockServer.getPort().toString());
        mockServer.stop();
        testContexts.put(context.getTestClass().get(), null);
        TalentFourApplication.main(new String[0]);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        testContexts.remove(context.getTestClass().get());
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ApplicationContext applicationContext = testContexts.get(extensionContext.getTestClass().get());
        Parameter parameter = parameterContext.getParameter();
        if (parameter.getType().equals(ApplicationContext.class)) {
            return applicationContext;
        }

        Value value = parameter.getAnnotation(Value.class);
        if (value != null) {
            return applicationContext.getEnvironment().resolvePlaceholders(value.value());
        } else if (parameter.getAnnotation(Autowired.class) != null) {
            return applicationContext.getBean(parameter.getType());
        }

        throw new IllegalArgumentException("Could not resolve parameter");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var entry = testContexts.entrySet().stream().filter(it -> it.getValue() == null).findFirst();
        entry.ifPresent(classApplicationContextEntry -> classApplicationContextEntry.setValue(event.getApplicationContext()));
    }
}