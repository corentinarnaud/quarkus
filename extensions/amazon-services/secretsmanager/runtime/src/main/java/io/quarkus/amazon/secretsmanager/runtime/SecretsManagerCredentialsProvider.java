package io.quarkus.amazon.secretsmanager.runtime;

import static io.quarkus.amazon.secretsmanager.runtime.CredentialsProviderConfig.SecretType.STRING;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.credentials.CredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@ApplicationScoped
@Named("secrets-manager-credentials-provider")
public class SecretsManagerCredentialsProvider implements CredentialsProvider {
    private static final Logger LOG = Logger.getLogger(SecretsManagerCredentialsProvider.class);

    @Inject
    private SecretsManagerClient syncClient;

    @Inject
    private SecretsManagerConfigHolder secretsManagerConfigHolder;

    @Inject
    private ObjectMapper objectMapper;

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {

        LOG.error("la config : " + getConfig().credentialsProvider);
        CredentialsProviderConfig config = getConfig().credentialsProvider.get(credentialsProviderName);

        if (config == null) {
            throw new SecretsManagerException("unknown credentials provider with name " + credentialsProviderName);
        }

        GetSecretValueResponse secretValue = syncClient.getSecretValue(r -> r.secretId(config.secretId));
        String secretString = config.secretType == STRING ? secretValue.secretString()
                : secretValue.secretBinary().asUtf8String();

        Map<String, String> secretMap;
        try {
            secretMap = objectMapper.readValue(secretString, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new SecretsManagerException("unable to parse the secret", e);
        }

        return secretMap;
    }

    private SecretsManagerBootstrapConfig getConfig() {
        return secretsManagerConfigHolder.getSecretsManagerBootstrapConfig();
    }
}
