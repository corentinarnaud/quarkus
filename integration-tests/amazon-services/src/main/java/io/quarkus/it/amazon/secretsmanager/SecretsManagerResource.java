package io.quarkus.it.amazon.secretsmanager;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.logging.Logger;

import io.quarkus.amazon.secretsmanager.runtime.SecretsManagerCredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerAsyncClient;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Path("/secretsmanager")
public class SecretsManagerResource {

    private static final Logger LOG = Logger.getLogger(SecretsManagerResource.class);
    private static final String TEXT = "Quarkus is awsome";
    private static final String CREDENTIALS = "{\"username\": \"quarkus\", \"password\": \"awesome\"}";
    private static final String SYNC_PARAM = "quarkus/sync-" + UUID.randomUUID().toString();
    private static final String ASYNC_PARAM = "quarkus/async-" + UUID.randomUUID().toString();
    private static final String CRED_PARAM = "quarkus/credential";

    @Inject
    SecretsManagerClient secretsManagerClient;

    @Inject
    SecretsManagerAsyncClient secretsManagerAsyncClient;

    @Inject
    SecretsManagerCredentialsProvider secretsManagerCredentialsProvider;

    @GET
    @Path("sync")
    @Produces(TEXT_PLAIN)
    public String testSync() {
        LOG.info("Testing Sync Secrets Manager client with secret name: " + SYNC_PARAM);
        //Put parameter
        secretsManagerClient.createSecret(r -> r.name(SYNC_PARAM).secretString(TEXT));
        //Get parameter
        return secretsManagerClient.getSecretValue(r -> r.secretId(SYNC_PARAM)).secretString();
    }

    @GET
    @Path("async")
    @Produces(TEXT_PLAIN)
    public CompletionStage<String> testAsync() {
        LOG.info("Testing Async Secrets Manager client with parameter: " + ASYNC_PARAM);
        //Put and get parameter
        return secretsManagerAsyncClient.createSecret(r -> r.name(ASYNC_PARAM).secretString(TEXT))
                .thenCompose(result -> secretsManagerAsyncClient.getSecretValue(r -> r.secretId(ASYNC_PARAM)))
                .thenApply(GetSecretValueResponse::secretString);
    }

    @GET
    @Path("credential")
    @Produces(TEXT_PLAIN)
    public Map<String, String> testCredentialProvider() {
        LOG.info("Testing Secrets Manager Credentials Provider with secret name: " + CRED_PARAM);
        //Put parameter
        secretsManagerClient.createSecret(r -> r.name(CRED_PARAM).secretString(CREDENTIALS));
        //Get parameter
        return secretsManagerCredentialsProvider.getCredentials("foo");
    }
}
