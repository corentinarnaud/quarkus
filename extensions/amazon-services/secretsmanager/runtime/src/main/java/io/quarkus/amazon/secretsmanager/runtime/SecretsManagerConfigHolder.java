package io.quarkus.amazon.secretsmanager.runtime;

import javax.inject.Singleton;

@Singleton
public class SecretsManagerConfigHolder {

    private SecretsManagerConfig secretsManagerConfig;

    public SecretsManagerConfig getSecretsManagerConfig() {
        return secretsManagerConfig;
    }

    public SecretsManagerConfigHolder setSecretsManagerConfig(SecretsManagerConfig secretsManagerConfig) {
        this.secretsManagerConfig = secretsManagerConfig;
        return this;
    }
}
