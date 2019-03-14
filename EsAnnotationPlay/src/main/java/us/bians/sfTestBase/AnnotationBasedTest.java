package us.bians.sfTestBase;

import junit.framework.Test;

public interface AnnotationBasedTest extends Test {
    @javax.annotation.Nonnull String getName();
}