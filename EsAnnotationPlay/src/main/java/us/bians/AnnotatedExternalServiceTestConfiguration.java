package us.bians;

public interface AnnotatedExternalServiceTestConfiguration {
    ExternalServiceTestInstanceConfiguration getTestConfiguration();
    ExternalServiceTestProperties.ESVersion getESVersion();
}
