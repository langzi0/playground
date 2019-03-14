package us.bians.designPattern.vistorPattern;
import java.util.Arrays;
import java.util.List;
/**
 * PACKAGE_NAME
 *Visitor Design Pattern in Java
 * PANKAJ 29 COMMENTS
 * https://www.journaldev.com/1769/visitor-design-pattern-java
 *
 * https://en.wikipedia.org/wiki/Visitor_pattern
 *
 * The following example is in the language Java, and shows how the contents of a tree of nodes (in this case describing the components of a car) can be printed. Instead of creating print methods for each node subclass (Wheel, Engine, Body, and Car), one visitor class (CarElementPrintVisitor) performs the required printing action. Because different node subclasses require slightly different actions to print properly, CarElementPrintVisitor dispatches actions based on the class of the argument passed to its visit method. CarElementDoVisitor, which is analogous to a save operation for a different file format, does likewise.
 *
 * Diagram
 * UML diagram of the Visitor pattern example with Car Elements
 *
 *
 *
 * @author xbian
 * @since
 */
public class VisitoryPatternMain implements Runnable {
     public void run(){
         Car car = new Car();

         car.accept(new CarElementPrintVisitor());
         car.accept(new CarElementDoVisitor());

    }
}


interface CarElement {
    void accept(CarElementVisitor visitor);
}

interface CarElementVisitor {
    void visit(Body body);
    void visit(Car car);
    void visit(Engine engine);
    void visit(Wheel wheel);
}

class Wheel implements CarElement {
    private final String name;

    public Wheel(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(CarElementVisitor visitor) {
        /*
         * accept(CarElementVisitor) in Wheel implements
         * accept(CarElementVisitor) in CarElement, so the call
         * to accept is bound at run time. This can be considered
         * the *first* dispatch. However, the decision to call
         * visit(Wheel) (as opposed to visit(Engine) etc.) can be
         * made during compile time since 'this' is known at compile
         * time to be a Wheel. Moreover, each implementation of
         * CarElementVisitor implements the visit(Wheel), which is
         * another decision that is made at run time. This can be
         * considered the *second* dispatch.
         */
        visitor.visit(this);
    }
}

class Body implements CarElement {
    @Override
    public void accept(CarElementVisitor visitor) {
        visitor.visit(this);
    }
}

class Engine implements CarElement {
    @Override
    public void accept(CarElementVisitor visitor) {
        visitor.visit(this);
    }
}

class Car implements CarElement {
    private final List<CarElement> elements;

    public Car() {
        this.elements = Arrays.asList(
                new Wheel("front left"), new Wheel("front right"),
                new Wheel("back left"), new Wheel("back right"),
                new Body(), new Engine()
        );
    }

    @Override
    public void accept(CarElementVisitor visitor) {
        for (CarElement element : elements) {
            element.accept(visitor);
        }
        visitor.visit(this);
    }
}

class CarElementDoVisitor implements CarElementVisitor {
    @Override
    public void visit(Body body) {
        System.out.println("Moving my body");
    }

    @Override
    public void visit(Car car) {
        System.out.println("Starting my car");
    }

    @Override
    public void visit(Wheel wheel) {
        System.out.println("Kicking my " + wheel.getName() + " wheel");
    }

    @Override
    public void visit(Engine engine) {
        System.out.println("Starting my engine");
    }
}

class CarElementPrintVisitor implements CarElementVisitor {
    @Override
    public void visit(Body body) {
        System.out.println("Visiting body");
    }

    @Override
    public void visit(Car car) {
        System.out.println("Visiting car");
    }

    @Override
    public void visit(Engine engine) {
        System.out.println("Visiting engine");
    }

    @Override
    public void visit(Wheel wheel) {
        System.out.println("Visiting " + wheel.getName() + " wheel");
    }
}

