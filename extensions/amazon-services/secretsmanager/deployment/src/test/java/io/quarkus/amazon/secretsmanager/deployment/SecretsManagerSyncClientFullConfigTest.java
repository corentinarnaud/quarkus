package io.quarkus.amazon.secretsmanager.deployment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.amazon.secretsmanager.runtime.SecretsManagerConfig;
import io.quarkus.amazon.secretsmanager.runtime.SecretsManagerCredentialsProvider;
import io.quarkus.test.QuarkusUnitTest;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerAsyncClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

public class SecretsManagerSyncClientFullConfigTest {

    @Inject
    SecretsManagerClient client;

    @Inject
    SecretsManagerAsyncClient async;

    @Inject
    SecretsManagerCredentialsProvider credentialsProvider;

    @Inject
    SecretsManagerConfig secretsManagerConfig;

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addAsResource("sync-urlconn-full-config.properties", "application.properties"));

    @Test
    public void test() {
        assertNotNull(secretsManagerConfig.credentialsProvider);
        System.out.println(secretsManagerConfig.credentialsProvider);
        assertEquals(secretsManagerConfig.credentialsProvider.size(), 1);
        // should finish with success
    }
}
