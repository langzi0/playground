package us.bians.externalServiceTest;

import junit.framework.TestSuite;
import us.bians.*;
import us.bians.sfTestBase.BaseTest;
import us.bians.types.ESVersions;

public class ExternalServiceRegistrationTestService extends AnnotatedExternalServiceBaseTest {

    private final BaseTest test;
    private final AnnotatedExternalServiceTestConfiguration testConfig;


    public ExternalServiceRegistrationTestService(AnnotatedExternalServiceTestConfiguration testConfig, BaseTest test) {
        this.test = test;
        this.testConfig = testConfig;
    }

    String registrationSchema = "";


    public static synchronized TestSuite suite() throws Exception {
        return DynamicExternalServiceTestGenerator.generate(ExternalServiceRegistrationTestService.class);
    }


    @ESVersions({ExternalServiceTestProperties.ESVersion.V1, ExternalServiceTestProperties.ESVersion.V2})
    public void someTest() {

        String esVersion = this.testSetup.getESVersion();

    }
}
