package io.quarkus.amazon.secretsmanager.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class SecretsManagerMapperRecorder {
    public static ObjectMapper objectMapper;

    public void initObjectMapper() {
        InstanceHandle<ObjectMapper> instance = Arc.container().instance(ObjectMapper.class);
        if (instance.isAvailable()) {
            objectMapper = instance.get().copy();
        }
        objectMapper = new ObjectMapper();
    }
}
