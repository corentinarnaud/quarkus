package io.quarkus.amazon.secretsmanager.runtime;

import javax.inject.Singleton;

@Singleton
public class SecretsManagerConfigHolder {

    private SecretsManagerBootstrapConfig secretsManagerBootstrapConfig;

    public SecretsManagerBootstrapConfig getSecretsManagerBootstrapConfig() {
        return secretsManagerBootstrapConfig;
    }

    void setSecretsManagerBootstrapConfig(SecretsManagerBootstrapConfig secretsManagerBootstrapConfig) {
        this.secretsManagerBootstrapConfig = secretsManagerBootstrapConfig;
    }
}
