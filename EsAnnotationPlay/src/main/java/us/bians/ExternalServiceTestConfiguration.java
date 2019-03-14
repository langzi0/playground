package us.bians;

public interface ExternalServiceTestConfiguration {
    String getTestSuffix();

    abstract class NullExternalServiceTestConfiguration implements ExternalServiceTestConfiguration {
        @Override public String getTestSuffix () {
        return null;
        }
    }

}
