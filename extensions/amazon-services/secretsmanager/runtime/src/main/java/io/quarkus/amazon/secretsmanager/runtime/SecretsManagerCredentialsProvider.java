package io.quarkus.amazon.secretsmanager.runtime;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.credentials.CredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@ApplicationScoped
@Named("secrets-manager-credentials-provider")
public class SecretsManagerCredentialsProvider implements CredentialsProvider {

    private SecretsManagerClient syncClient;

    private SecretsManagerConfigHolder secretsManagerConfigHolder;

    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {

        CredentialsProviderConfig config = getConfig().credentialsProvider.get(credentialsProviderName);

        if (config == null) {
            throw new SecretsManagerException("unknown credentials provider with name " + credentialsProviderName);
        }

        GetSecretValueResponse secretValue = syncClient.getSecretValue(r -> r.secretId(config.secretId));
        Map<String, String> secretMap;
        try {
            secretMap = objectMapper.readValue(secretValue.secretString(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new SecretsManagerException("unable to parse the secret", e);
        }

        String username = secretMap.get(USER_PROPERTY_NAME);
        String password = secretMap.get(PASSWORD_PROPERTY_NAME);

        if (username != null && password != null) {
            Map<String, String> result = new HashMap<>();
            result.put(USER_PROPERTY_NAME, username);
            result.put(PASSWORD_PROPERTY_NAME, password);
            return result;
        }

        throw new SecretsManagerException("Property \"username\" and \"password\" must be present in the secret");
    }

    private SecretsManagerConfig getConfig() {
        return secretsManagerConfigHolder.getSecretsManagerConfig();
    }
}
