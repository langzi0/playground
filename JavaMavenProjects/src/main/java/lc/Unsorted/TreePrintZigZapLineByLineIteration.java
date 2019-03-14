package lc.Unsorted;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lc.Common.Category;
import lc.Common.InvokableBase;
import lc.Common.Priority;
import lc.Question.BSTIterator.TreeNode;

/**
 * @author
 * @since 10/28/15 This is the template for adding any new class
 */

public class TreePrintZigZapLineByLineIteration extends InvokableBase {

  // Add this class to lc.Common/LeetCodeMain.java
  @Override
  public Priority getRunPriority() {
    return new Priority(160101, 0, Category.LeetCode);
  }

  @Override
  public void run() {
    //Call your test in lc.Common/LeetCodeMain.java
  }

  TreeNode node;

  public List<TreeNode> PrintList(TreeNode node) {
    List<TreeNode> ret = new ArrayList<>();
    if (node == null) {
      return ret;
    }

    List<TreeNode> curLine = new ArrayList<>();
    curLine.add(node);
    while (!curLine.isEmpty()) {
      LinkedList<TreeNode> nextLine = new LinkedList<>();
      for(TreeNode t : curLine)
      {
        ret.add(t);
        if (t.left != null) nextLine.add(0, t.left);
        if (t.right != null) nextLine.add(0, t.right);
      }
      curLine = nextLine;
    }

    return ret;
  }


  public List<List<TreeNode>> getLines(TreeNode root)
  {
    List<List<TreeNode>> ret = new ArrayList<>();

    // line by line


    List<TreeNode> curLine = new ArrayList<>();
    curLine.add(root);


    while(curLine.size() >0)
    {
      ret.add(curLine);
      List<TreeNode> nextLine = new ArrayList<>();

      for(TreeNode e : curLine)
      {
        if (e.left != null) nextLine.add(e.left);
        if (e.right != null) nextLine.add(e.right);
      }
      curLine = nextLine;
    }




    return ret;

  }
}

