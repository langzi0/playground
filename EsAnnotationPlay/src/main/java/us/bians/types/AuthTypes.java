package us.bians.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import us.bians.ExternalServiceTestProperties;
import us.bians.ExternalServiceTestProperty;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ExternalServiceTestProperty
public @interface AuthTypes {
    ExternalServiceTestProperties.AuthType[] value();
}


