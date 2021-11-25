package io.quarkus.amazon.secretsmanager.runtime;

import java.util.Map;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "secretsmanager", phase = ConfigPhase.BOOTSTRAP)
public class SecretsManagerBootstrapConfig {

    /**
     * List of named credentials providers, such as: `quarkus.secretsmanager.credentials-provider.foo.secretId=mysecret
     * <p>
     * This defines a credentials provider `foo` returning key `password` from secret id `mysecret`.
     * The secret must contain a `username` and a `password` field
     * Once defined, this provider can be used in credentials consumers, such as the Agroal connection pool.
     * <p>
     * Example: `quarkus.datasource.credentials-provider=foo`
     */
    @ConfigItem
    public Map<String, CredentialsProviderConfig> credentialsProvider;

}
