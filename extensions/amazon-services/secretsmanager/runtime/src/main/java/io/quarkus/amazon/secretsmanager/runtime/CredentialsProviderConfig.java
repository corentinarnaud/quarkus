package io.quarkus.amazon.secretsmanager.runtime;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

@ConfigGroup
public class CredentialsProviderConfig {

    /**
     * A secretId in Secrets Manager, where we will find the secret.
     */
    @ConfigItem
    public String secretId;

}
