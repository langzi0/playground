package us.bians;

import us.bians.sfTestBase.AnnotationBasedTestProperty;
import us.bians.types.AuthTypes;
import us.bians.types.ESVersions;

import java.lang.annotation.Annotation;
import java.util.Map;

public class ExternalServiceTestConfigurationBuilder implements ExternalServiceTestConfiguration {
    private final String testSuffix;

    protected final Map<Class<? extends Annotation>, AnnotationBasedTestProperty> expandedProperties;

    public ExternalServiceTestConfigurationBuilder(Map<Class<? extends Annotation>, AnnotationBasedTestProperty> expandedProperties) {
        this.expandedProperties = expandedProperties;
        this.testSuffix = createSuffixName();
    }

    protected String createSuffixName() {
        StringBuilder sb = new StringBuilder();
        append(sb, ESVersions.class);
        append(sb, AuthTypes.class);
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return "[" + sb.toString() + "]";
    }

    private void append(StringBuilder sb, Class<? extends Annotation> annotationType) {
        AnnotationBasedTestProperty prop = this.expandedProperties.get(annotationType);
        if (prop != null) {
            sb.append(prop.toString());
            sb.append("|");
        }
    }


    @Override
    public final String getTestSuffix() {
        return this.testSuffix;
    }

    @Override
    public String getTestSuffix() {
        return null;
    }
}
