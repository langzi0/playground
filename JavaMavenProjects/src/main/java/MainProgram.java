import lc.Common.LeetCodeMain;
import us.bians.annotation.AnnotationTests;
import us.bians.designPattern.vistorPattern.VisitoryPatternMain;

import java.util.Arrays;
import java.util.List;

public class MainProgram {


    public static void main(String[] args) throws Exception {
        // ToRun has a runnable and a willRun tag to be used to determin set your the runnable
        // to run or not

        List<ToRun> toRunList = Arrays.asList(
                new ToRun(new LeetCodeMain(), true),
                new ToRun(new AnnotationTests(), false),
                new ToRun(new VisitoryPatternMain(), false)
                // Add a new ToRun here, each runnable can start by itself by calling run();
        );

        invoke(toRunList);

    }

    static private void invoke(List<ToRun> toRunList) {
        toRunList.forEach(toRun -> {
            if (toRun.willRun)
                toRun.runnable.run();
        });
    }

}
