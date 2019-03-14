package us.bians.annotation; /**
 * PACKAGE_NAME
 *
 * @author xbian
 * @since 204
 */
import java.lang.annotation.*;

/**
 * Indicates that the annotated method is a test method.
 * This annotation should be used only on parameterless static methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MyAnnotation { }