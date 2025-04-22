package org.eclipse.jgit.treewalk;

import static org.eclipse.jgit.merge.ResolveMerger.T_BASE;
import static org.eclipse.jgit.merge.ResolveMerger.T_FILE;
import static org.eclipse.jgit.merge.ResolveMerger.T_INDEX;
import static org.eclipse.jgit.merge.ResolveMerger.T_OURS;
import static org.eclipse.jgit.merge.ResolveMerger.T_THEIRS;

import java.io.IOException;
import java.util.Set;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.RenameResolver;
import org.eclipse.jgit.merge.RenameResolver.RenameEntry;
import org.eclipse.jgit.merge.RenameResolver.RenameType;
import org.eclipse.jgit.merge.ResolveMerger;

public class RenameProcessingTreeWalk  extends NameConflictTreeWalk {

  private String renamePath;

  private final RenameResolver renameResolver;

  private final boolean skipRenames;
  private final IndexChecker indexChecker;
  private final WorktreeChecker worktreeChecker;
  boolean isTerminated =  false;


  public RenameProcessingTreeWalk(Repository repo
    super(repo);
    this.renameResolver = renameResolver;
    this.skipRenames = skipRenames;
    this.indexChecker = indexChecker;
    this.worktreeChecker = worktreeChecker;
  }

  public RenameProcessingTreeWalk(@Nullable Repository repo
    super(repo
    this.renameResolver = renameResolver;
    this.skipRenames = skipRenames;
    this.indexChecker = indexChecker;
    this.worktreeChecker = worktreeChecker;
  }

  public RenameProcessingTreeWalk(ObjectReader or
      RenameResolver renameResolver
    super(or);
    this.renameResolver = renameResolver;
    this.skipRenames = skipRenames;
    this.indexChecker = indexChecker;
    this.worktreeChecker = worktreeChecker;
  }

  public interface IndexChecker {
    boolean isIndexDirty();
  }

  public interface WorktreeChecker {
    boolean isWorktreeDirty() throws IOException;
  }

  public boolean isTerminated(){
    return isTerminated;
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
    if(indexChecker.isIndexDirty()){
      this.isTerminated = true;
      return false;
    }
    if(renameResolver.isRenameEntry(getTree(T_BASE)
       if(!keepRenameCorrelations()){
         if(isTerminated){
           return false;
         }
         return next();
       }
    }
    return true;
  }

  public void swapTree(int nth
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

  private AbstractTreeIterator[] matchTrees;

  private void keepTrees(Set<Integer> treesToKeep) {
    matchTrees = new AbstractTreeIterator[trees.length];
    for (int i = 0; i < trees.length; i++) {
      matchTrees[i] = this.trees[i].matches;
      if (!treesToKeep.contains(i)) {
        this.trees[i].matches = null;
      }
    }
  }

  private void swapMatchBack() {
    if (matchTrees != null) {
      for (int i = 0; i < matchTrees.length; i++) {
        this.trees[i].matches = matchTrees[i];
      }
      matchTrees = null;
    }
  }


  private boolean keepRenameCorrelations( )
      throws IOException {

    CanonicalTreeParser base =
        getTree(T_BASE
    if(base == null){
      return false;
    }
    String basePath = base.getEntryPathString();
    RenameType renameType = renameResolver.getRenameType(basePath);
    if (renameType.equals(RenameType.NO_RENAME) || renameType.equals(RenameType.RENAME_CONFLICT)) {
      return false;
    }
    RenameEntry renameEntry = renameResolver.getRenameEntry(basePath);
    if (renameResolver.isBaseRename(renameEntry.getTargetPath())) {
      return false;
    }
    Set<Integer> keepTrees = Set.of();
    if (renameType.equals(RenameType.RENAME_IN_OURS)) {
      if(this.getTree(T_OURS)!=null) {
        keepTrees = Set.of(T_OURS
      }
    } else if (renameType.equals(RenameType.RENAME_IN_THEIRS)) {
      if(this.getTree(T_THEIRS)!=null) {
        keepTrees = Set.of(T_THEIRS);
      }
    } else if (renameType.equals(RenameType.RENAME_BOTH_NO_CONFLICT)) {
      if(this.getTree(T_OURS)!=null || this.getTree(T_THEIRS)!=null){
        keepTrees = Set.of(T_OURS
      }
    }
    if(keepTrees.isEmpty()) {
      return false;
    }
    if(worktreeChecker.isWorktreeDirty()){
      isTerminated = true;
      return false;
    }
    keepTrees(keepTrees);
    return true;
  }
}
