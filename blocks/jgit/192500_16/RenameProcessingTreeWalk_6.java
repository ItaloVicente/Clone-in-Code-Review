package org.eclipse.jgit.treewalk;
;
import static org.eclipse.jgit.merge.RenameResolver.RenameConflict.RENAME_BOTH_SIDES_CONFLICT;
import static org.eclipse.jgit.merge.ResolveMerger.T_BASE;
import static org.eclipse.jgit.merge.ResolveMerger.T_FILE;
import static org.eclipse.jgit.merge.ResolveMerger.T_INDEX;
import static org.eclipse.jgit.merge.ResolveMerger.T_OURS;
import static org.eclipse.jgit.merge.ResolveMerger.T_THEIRS;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.RenameResolver;
import org.eclipse.jgit.merge.RenameResolver.RenameEntry;
import org.eclipse.jgit.merge.RenameResolver.RenameType;

public class RenameProcessingTreeWalk  extends NameConflictTreeWalk {

  private String renamePath;

  private final RenameResolver renameResolver;

  private final boolean skipRenames;

  public RenameProcessingTreeWalk(Repository repo
    super(repo); this.renameResolver = renameResolver;
    this.skipRenames = skipRenames;
  }

  public RenameProcessingTreeWalk(@Nullable Repository repo
    super(repo
    this.renameResolver = renameResolver;
    this.skipRenames = skipRenames;
  }

  public RenameProcessingTreeWalk(ObjectReader or
      RenameResolver renameResolver
    super(or);
    this.renameResolver = renameResolver;
    this.skipRenames = skipRenames;
  }

  private AbstractTreeIterator getTree(final int nth){
    return getTree(nth
  }

  @Override
  public boolean next() throws IOException {
    if(!skipRenames){
      return super.next();
    }
    swapMatchBack();
    boolean moreEntries = super.next();
    if(!moreEntries){
      return false;
    }
    if(renameResolver.isRenameEntry(getTree(T_BASE)
       if(!keepRenameCorrelations()){
         return next();
       }
    }
    return true;
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

  public void keepTrees(Set<Integer> treesToKeep) {
    matchTrees = new AbstractTreeIterator[trees.length];
    for (int i = 0; i < trees.length; i++) {
      matchTrees[i] = this.trees[i].matches;
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


  public boolean keepRenameCorrelations( )
      throws IOException {

    CanonicalTreeParser base =
        getTree(T_BASE
    RenameType renameType = renameResolver.getRenameType(base);
    if (renameType.equals(RenameType.NO_RENAME) || renameType.equals(RenameType.RENAME_CONFLICT)) {
      return false;
    }
    RenameEntry renameEntry = renameResolver.getRenameEntry(base);
    if (renameResolver.isBaseRename(renameEntry.targetPath)) {
      return false;
    }
    if (renameType.equals(RenameType.RENAME_IN_OURS)) {
      keepTrees(Set.of(T_OURS
      return true;
    } else if (renameType.equals(RenameType.RENAME_IN_THEIRS)) {
      keepTrees(Set.of(T_THEIRS));
      return true;
    } else if (renameType.equals(RenameType.RENAME_BOTH_NO_CONFLICT)) {
      keepTrees(Set.of(T_OURS
      return true;
    }
    return false;
  }
}
