import java.util.Arrays;
import java.util.List;

import us.bians.annotation.AnnotationTests;
import us.bians.designPattern.vistorPattern.VisitoryPatternMain;

public class Main {


    public static void main(String[] args) throws Exception {

        List<ToRun> studyUnits = Arrays.asList(
                new ToRun(new AnnotationTests(), false),
                new ToRun(new VisitoryPatternMain(), true)
                // Add a new ToRun here, each runnable can start by itself by calling run();
        );


        studyUnits.forEach(studyUnit -> {
            if (studyUnit.willRun)
                studyUnit.runnable.run();
        });
    }


}
