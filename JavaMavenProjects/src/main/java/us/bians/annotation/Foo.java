package us.bians.annotation;

/**
 * PACKAGE_NAME
 *
 * @author xbian
 * @since 204
 */
@MyAnnotation
public class Foo {
    @MyAnnotation
    public static void m1() { }
    public static void m2() { }
    @MyAnnotation
    public static void m3() {
        throw new RuntimeException("Boom");
    }
    public static void m4() { }
    @MyAnnotation
    public static void m5() { }
    public static void m6() { }
    @MyAnnotation
    public static void m7() {
        throw new RuntimeException("Crash");
    }
    public static void m8() { }
}