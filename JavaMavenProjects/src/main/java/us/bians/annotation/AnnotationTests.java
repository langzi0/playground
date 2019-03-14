package us.bians.annotation;

/**
 * us.bians
 *
 * @author xbian
 * @since 204
 */
import java.lang.reflect.*;

public class AnnotationTests implements Runnable{
    @Override
    public void run() {
        try {

            String classNameToCheck = Foo.class.getName();
            int passed = 0, failed = 0;
            // Only invoke annotated methods.
            if (Foo.class.isAnnotationPresent(MyAnnotation.class)) {
                System.out.printf("whole class %s is annotated with : %s\n", classNameToCheck, MyAnnotation.class.getName());

                for (Method m : Class.forName(classNameToCheck).getMethods()) {
                    if (m.isAnnotationPresent(MyAnnotation.class)) {
                        // this method has the MyAnnotation annotation.
                        try {
                            m.invoke(null);
                            passed++;
                        } catch (Throwable ex) {
                            // Annotated but throw exception during invoke, so we will count as not passing.
                            System.out.printf("MyAnnotation %s failed: %s %n", m, ex.getCause());
                            failed++;
                        }
                    }
                }
            }
            System.out.printf("Passed: %d, Failed %d%n", passed, failed);

        } catch (Exception e) {
        }
    }
}