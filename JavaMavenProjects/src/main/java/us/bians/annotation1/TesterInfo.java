package us.bians.annotation1;

/**
 * us.bians.us.bians.annotation1
 *
 * @author xbian
 * @since 204
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //on class level
public @interface TesterInfo {



    Priority priority() default Priority.MEDIUM;

    String[] tags() default "";

    String createdBy() default "Mkyong";

    String lastModified() default "03/01/2014";

}