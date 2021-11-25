package io.quarkus.amazon.secretsmanager.deployment;

import javax.inject.Inject;

import io.quarkus.amazon.secretsmanager.runtime.SecretsManagerBootstrapConfig;
import io.quarkus.amazon.secretsmanager.runtime.SecretsManagerConfigHolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.amazon.secretsmanager.runtime.SecretsManagerCredentialsProvider;
import io.quarkus.test.QuarkusUnitTest;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerAsyncClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecretsManagerSyncClientFullConfigTest {

    @Inject
    SecretsManagerClient client;

    @Inject
    SecretsManagerAsyncClient async;

    @Inject
    SecretsManagerCredentialsProvider credentialsProvider;

    @Inject SecretsManagerConfigHolder secretsManagerConfigHolder;

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addAsResource("sync-urlconn-full-config.properties", "application.properties"));

    @Test
    public void test() {
        SecretsManagerBootstrapConfig secretsManagerBootstrapConfig = secretsManagerConfigHolder.getSecretsManagerBootstrapConfig();
        assertNotNull(secretsManagerBootstrapConfig);
        // should finish with success
    }
}
