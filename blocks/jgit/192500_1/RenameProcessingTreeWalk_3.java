package org.eclipse.jgit.treewalk;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public class RenameProcessingTreeWalk  extends NameConflictTreeWalk{

  private static final int TREE_MODE = FileMode.TREE.getBits();

  private boolean fastMinHasMatch;

  private String renamePath;

  public RenameProcessingTreeWalk (Repository repo) {
    super(repo);
  }

  public RenameProcessingTreeWalk (@Nullable Repository repo
    super(repo
  }

  public RenameProcessingTreeWalk (ObjectReader or) {
    super(or);
  }

  public void swapRenameTree(int nth
    trees[nth] = tree;
    trees[nth].matches = currentHead;
  }

  public void setPathName(String pathName){
    this.renamePath = pathName;
  }

  @Override
  public byte[] getRawPath() {
    return renamePath.getBytes();
  }

  @Override
  public String getPathString() {
    return renamePath;
  }
}
