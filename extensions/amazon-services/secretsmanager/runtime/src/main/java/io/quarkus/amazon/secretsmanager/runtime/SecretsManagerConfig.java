package io.quarkus.amazon.secretsmanager.runtime;

import java.util.Map;

import io.quarkus.amazon.common.runtime.AwsConfig;
import io.quarkus.amazon.common.runtime.NettyHttpClientConfig;
import io.quarkus.amazon.common.runtime.SdkConfig;
import io.quarkus.amazon.common.runtime.SyncHttpClientConfig;
import io.quarkus.runtime.annotations.ConfigDocSection;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "secretsmanager", phase = ConfigPhase.RUN_TIME)
public class SecretsManagerConfig {

    /**
     * AWS SDK client configurations
     */
    @ConfigItem(name = ConfigItem.PARENT)
    @ConfigDocSection
    public SdkConfig sdk;

    /**
     * AWS services configurations
     */
    @ConfigItem
    @ConfigDocSection
    public AwsConfig aws;

    /**
     * Sync HTTP transport configurations
     */
    @ConfigItem
    @ConfigDocSection
    public SyncHttpClientConfig syncClient;

    /**
     * Netty HTTP transport configurations
     */
    @ConfigItem
    @ConfigDocSection
    public NettyHttpClientConfig asyncClient;

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
