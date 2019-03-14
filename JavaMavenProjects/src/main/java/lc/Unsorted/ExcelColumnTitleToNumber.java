package lc.Unsorted;

import lc.Common.Category;
import lc.Common.InvokableBase;
import lc.Common.Priority;
import lc.Common.Util;

/**
 * @author
 * @since 10/28/15 This is the template for adding any new class
 */

public class ExcelColumnTitleToNumber extends InvokableBase {

  // Add this class to lc.Common/LeetCodeMain.java
  @Override
  public Priority getRunPriority() {
    return new Priority(160201, 0, Category.LeetCode);
  }

  @Override
  public void run() {
    //Call your test in lc.Common/LeetCodeMain.java
    Util.assertEq(1, titleToNumber("A"));
    Util.assertEq(27, titleToNumber("AA"));

  }

  public int titleToNumber(String s) {
    // "AAAA" ==> 26 + 26* 26 + 26 * 26 * 26
    int num = 0;
    int base = 1;
    int i = 0;
    int value = (int) s.charAt(0) - (int) 'A';
    while (i < s.length() - 1) {
      base *= 26;
      value = value * 26 + (int) s.charAt(i + 1) - (int) 'A';
      num += base;
      i++;
    }
    return value + num + 1;

  }

}
