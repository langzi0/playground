package us.bians;

//  Annotations config values for an External Service test instance used in a given test.

public interface ExternalServiceTestInstanceConfiguration {
    String getTestSuffix();
    ExternalServiceTestProperties.UserTestType getUserTestType();


}
