package us.bians;

import junit.framework.TestSuite;
import test.runner.IgnoreFailureReason;
import us.bians.externalServiceTest.ExternalServiceRegistrationTestService;
import us.bians.sfTestBase.AnnotationBasedTest;
import us.bians.sfTestBase.AnnotationBasedTestProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import test.runner.TestLabels;

public class DynamicExternalServiceTestGenerator {


    private static final String TEST_NAME_SEPARATOR = " ";

    // AnnotationBasedTest class used to generate test methods
    private final Class<? extends AnnotationBasedTest> testClass;

    private Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> propertyMapAtClassLevel;


    public DynamicExternalServiceTestGenerator(Class<? extends AnnotationBasedTest> testClass) {
        this.testClass = testClass;
        this.propertyMapAtClassLevel = buildPropertyMapAtClassLevel();
    }


    public static TestSuite generate(Class<AnnotationBasedTest> annotationBasedTestClass) {

        return new DynamicExternalServiceTestGenerator(annotationBasedTestClass).generateTests();
    }



    public synchronized TestSuite generateTests() {
        TestSuite suite = new TestSuite(testClass.getName());
        for (Method method : testClass.getMethods()) {
            if (isTestMethod(method)) {
                for (ExternalServiceTestConfigurationBuilder builder : generateTestBuilders(method)) {
                    suite.addTest(newTestInstance(method.getName(), testClass, builder));
                }
            }
        }
        return suite;
    }

    private List<ExternalServiceTestConfigurationBuilder> generateTestBuilders(Method testMethod) {
        List<Map<Class<? extends Annotation>, AnnotationBasedTestProperty>> expandedProperties = handleAnnotations(testMethod);
        List<ExternalServiceTestConfigurationBuilder> builders = new ArrayList<>();
        for (Map<Class<? extends Annotation>, AnnotationBasedTestProperty> map : expandedProperties) {
            ExternalServiceTestConfigurationBuilder builder = newTestBuilderInstance(getTestBuilderClass(), testMethod.getName(), map);
            if (builder != null) {
                builders.add(builder);
            }
        }
        return builders;
    }

    List<Map<Class<? extends Annotation>, AnnotationBasedTestProperty>> handleAnnotations(Method testMethod) {
        Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> propertyMap = buildPropertyMap(testMethod);
        List<Map<Class<? extends Annotation>, AnnotationBasedTestProperty>> expandedProperties = new ArrayList<>();
        expandedProperties.add(new HashMap<>());

        // process the annotations in multiple passes
        Set<Map.Entry<Class<? extends Annotation>, AnnotationBasedTestProperty[]>> remainingAnnotationEntries = propertyMap.entrySet();
        Set<Class<? extends Annotation>> handledAnnotationClasses = new HashSet<>();
        while (!remainingAnnotationEntries.isEmpty()) {
            Set<Map.Entry<Class<? extends Annotation>, AnnotationBasedTestProperty[]>> annotationsEntriesHandledThisPass = new HashSet<>();
            for (Map.Entry<Class<? extends Annotation>, AnnotationBasedTestProperty[]> entry : remainingAnnotationEntries) {
                boolean shouldHandleThisPass = true;
                if (isTestPropertyConditional(entry.getKey())) {
                    Class<? extends Annotation> prerequisite = entry.getKey().getAnnotation(ExternalServiceTestProperty.class).prerequisite();
                    shouldHandleThisPass = handledAnnotationClasses.contains(prerequisite);
                }
                if (shouldHandleThisPass) {
                    expand(expandedProperties, entry.getKey(), entry.getValue());
                    annotationsEntriesHandledThisPass.add(entry);
                    handledAnnotationClasses.add(entry.getKey());
                }
            }
            remainingAnnotationEntries.removeAll(annotationsEntriesHandledThisPass);
            //Prevent infinite loops from misconfigured tests
            if (annotationsEntriesHandledThisPass.isEmpty()) {
                throw new RuntimeException("Your test class isn't annotated correctly");
            }
        }

        return expandedProperties;
    }

    private boolean isTestPropertyGenerator(Annotation annotation) {
        return annotation.annotationType().isAnnotationPresent(ExternalServiceTestProperty.class);
    }

    private boolean isTestPropertyConditional(Class<? extends Annotation> annotationType) {
        return annotationType.isAnnotationPresent(ExternalServiceTestProperty.class)
                && annotationType.getAnnotation(ExternalServiceTestProperty.class).prerequisite() != ExternalServiceTestProperty.DEFAULT.class;
    }

    private Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> getPropertyMapAtClassLevel() {
        return this.propertyMapAtClassLevel;
    }

    private Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> buildPropertyMap(Method testMethod) {
        Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> propertyMapAtMethodLevel = new HashMap<>();
        Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> propertyMap = new HashMap<>();

        //Retrieve all the properties to use for this test declared at the test or class level
        for (Annotation annotation : testMethod.getAnnotations())
            if (isTestPropertyGenerator(annotation)) {
                AnnotationBasedTestProperty[] propertiesAtClassLevel = getPropertyMapAtClassLevel().get(annotation.annotationType());
                AnnotationBasedTestProperty[] propertiesAtMethodLevel = retrieveTestProperties(annotation);
                AnnotationBasedTestProperty[] properties = propertiesAtMethodLevel != null
                        ? propertiesAtMethodLevel
                        : propertiesAtClassLevel != null
                        ? propertiesAtClassLevel
                        : new AnnotationBasedTestProperty[]{null};

                propertyMapAtMethodLevel.put(annotation.annotationType(), propertiesAtMethodLevel);
                propertyMap.put(annotation.annotationType(), properties);
            }

        //Add the properties that have been set at the class level only
        for (Map.Entry<Class<? extends Annotation>, AnnotationBasedTestProperty[]> entry : getPropertyMapAtClassLevel().entrySet())
            if (propertyMap.get(entry.getKey()) == null) {
                propertyMap.put(entry.getKey(), entry.getValue());
            }

        return propertyMap;
    }

    private static boolean isTestMethod(Method method) {
        if (method.isAnnotationPresent(TestLabels.class)) {
            TestLabels testLabels = method.getAnnotation(TestLabels.class);
            if (testLabels.value().length == 1 && testLabels.value()[0].contains(IgnoreFailureReason.IGNORE)) {
                return false;
            }
        }
        return Modifier.isPublic(method.getModifiers())
                && method.getName().startsWith("test")
                && method.getParameterTypes().length == 0
                && method.getReturnType().equals(Void.TYPE);
    }

    private Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> buildPropertyMapAtClassLevel() {
        Map<Class<? extends Annotation>, AnnotationBasedTestProperty[]> propertyMapAtClassLevel = new HashMap<>();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        Class curClazz = testClass;
        while (curClazz.getSuperclass() != null) {
            classes.add(curClazz.getSuperclass());
            curClazz = curClazz.getSuperclass();
        }
        classes.add(testClass);
        List<Annotation> annotations = new ArrayList<>();
        for (Class clazz : classes) {
            annotations.addAll(Arrays.asList(clazz.getAnnotations()));
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(ExternalServiceTestProperty.class)) {
                propertyMapAtClassLevel.put(annotation.annotationType(), retrieveTestProperties(annotation));
            }
        }

        return propertyMapAtClassLevel;
    }

    private AnnotationBasedTestProperty[] retrieveTestProperties(Annotation annotation) {
        try {
            Method method = annotation.annotationType().getMethod("value");
            return (AnnotationBasedTestProperty[]) method.invoke(annotation);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
