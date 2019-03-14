package us.bians;

import us.bians.sfTestBase.BaseTest;

public class ExternalServiceTestSetup<T extends BaseTest & AnnotatedExternalServiceTestConfiguration> implements AnnotatedExternalServiceTestConfiguration{
    ExternalServiceTestInstanceConfiguration getTestConfiguration(){return null;};


}
