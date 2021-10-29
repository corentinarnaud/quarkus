package io.quarkus.amazon.secretsmanager.runtime;

public class SecretsManagerException extends RuntimeException {

    public SecretsManagerException() {
    }

    public SecretsManagerException(String message) {
        super(message);
    }

    public SecretsManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecretsManagerException(Throwable cause) {
        super(cause);
    }

    public SecretsManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}