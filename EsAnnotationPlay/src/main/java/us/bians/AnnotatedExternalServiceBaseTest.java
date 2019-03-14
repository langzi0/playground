package us.bians;

import us.bians.sfTestBase.BaseTest;
import us.bians.sfCodeBase.ExternalService;
import us.bians.testutils.ExternalServiceTestingUtil;

public class AnnotatedExternalServiceBaseTest extends BaseTest implements AnnotatedExternalServiceTestConfiguration{



    // Autonumber mask (value format for auto number fields) defined in MetaTestingUtil as "ftest-cf-{0}"
    protected static final String META_TESTING_UTIL_AUTO_NUMBER_PREFIX = "ftest-cf-";


    protected ExternalServiceTestInstanceConfiguration testConfig;
    protected ExternalServiceTestSetup<AnnotatedExternalServiceBaseTest> testSetup;

    protected ExternalService testServiceInterface;

    protected ExternalServiceTestingUtil testUtil;

}
