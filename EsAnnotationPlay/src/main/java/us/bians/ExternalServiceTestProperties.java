package us.bians;

public interface ExternalServiceTestProperties {

    // Add all enum ExternalServiceTestProperties here.
    enum ESVersion implements ExternalServiceTestProperties {

        V1("V1"),
        V2("V2");

        private final String name;

        ESVersion(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

    enum ESDataType implements ExternalServiceTestProperties {

        INTEGER("Integer"),
        OBJECT("Object");

        private final String name;

        ESDataType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

    enum AuthType implements ExternalServiceTestProperties {

        NONE("None"),
        OAUTH("Oauth"),
        PASSWORD("Password");
        private final String name;

        AuthType(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    enum UserTestType implements ExternalServiceTestProperties {
        ADMIN,
        STANDARD,
        COMMUNITY;
    }

    enum EditionTestType implements ExternalServiceTestProperties {
        PROFESSIONAL_EDITION,
        ENTERPRISE_EDITION;
    }
}
