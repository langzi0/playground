import com.sun.tools.javac.util.Assert;

/**
 * PACKAGE_NAME
 *
 * @author xbian
 * @since 208
 */
public class FindNextLargerIdx {

    void test()
    {
       Integer[] temperatures = new Integer{73, 74, 75, 71, 69, 72, 76, 73}
        Integer[] out = new Integer[]{1, 1, 4, 2, 1, 1, 0, 0};
        () -> assertEquals(dailyTemperatures(temperatures), out);
    }

    public int[] dailyTemperatures(int[] temperatures) {
        // Need to keep left side ray shadow.
        // only look the next biggest chain.

        int i = temperatures.size() - 1;



    }
}
原告被告家庭地理位置