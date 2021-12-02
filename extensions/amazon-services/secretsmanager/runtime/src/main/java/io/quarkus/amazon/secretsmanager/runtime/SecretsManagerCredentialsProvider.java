package io.quarkus.amazon.secretsmanager.runtime;

import static io.quarkus.amazon.secretsmanager.runtime.CredentialsProviderConfig.SecretType.STRING;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.credentials.CredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@ApplicationScoped
@Named("secrets-manager-credentials-provider")
public class SecretsManagerCredentialsProvider implements CredentialsProvider {

    @Inject
    private SecretsManagerClient syncClient;

    @Inject
    private SecretsManagerConfig secretsManagerConfig;

    @Inject
    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {

        CredentialsProviderConfig config = secretsManagerConfig.credentialsProvider.get(credentialsProviderName);

        if (config == null) {
            throw new SecretsManagerException("Unknown credentials provider with name " + credentialsProviderName);
        }

        if (syncClient == null) {
            throw new SecretsManagerException("SecretManagerClient has not be configured");
        }
        GetSecretValueResponse secretValue = syncClient.getSecretValue(r -> r.secretId(config.secretId));
        String secretString = config.type == STRING ? secretValue.secretString()
                : secretValue.secretBinary().asUtf8String();

        Map<String, String> secretMap;
        try {
            secretMap = objectMapper.readValue(secretString, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new SecretsManagerException("Unable to parse the secret", e);
        }

        return secretMap;
    }

}
