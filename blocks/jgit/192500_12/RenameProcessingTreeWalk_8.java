package org.eclipse.jgit.treewalk;

import java.util.Arrays;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public class RenameProcessingTreeWalk  extends NameConflictTreeWalk {

  private static final int TREE_MODE = FileMode.TREE.getBits();

  private boolean fastMinHasMatch;

  private String renamePath;

  public RenameProcessingTreeWalk(Repository repo) {
    super(repo);
  }

  public RenameProcessingTreeWalk(@Nullable Repository repo
    super(repo
  }

  public RenameProcessingTreeWalk(ObjectReader or) {
    super(or);
  }

  public void swapRenameTree(int nth
    trees[nth] = tree;
    trees[nth].matches = currentHead;
  }

  public void setPathName(String pathName) {
    this.renamePath = pathName;
  }

  @Override
  public byte[] getRawPath() {
    return renamePath != null ? renamePath.getBytes() : super.getRawPath();
  }

  @Override
  public String getPathString() {
    return renamePath != null ? renamePath : super.getPathString();
  }

  AbstractTreeIterator[] matchTrees;
  public void swapRenames(AbstractTreeIterator[] swapTrees) {
    matchTrees = new AbstractTreeIterator[swapTrees.length];
    for (int i = 0; i < swapTrees.length; i++) {
      matchTrees[i] = this.trees[i].matches;
      if (swapTrees[i] == null && this.getTree(i
        this.trees[i].matches = null;
      }
    }
  }
  public void swapMatchBack() {
    if (matchTrees != null) {
      for (int i = 0; i < matchTrees.length; i++) {
        this.trees[i].matches = matchTrees[i];
      }
      matchTrees = null;
    }
  }
}
