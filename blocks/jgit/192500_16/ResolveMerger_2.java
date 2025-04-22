package org.eclipse.jgit.merge;

import static org.eclipse.jgit.merge.ResolveMerger.T_FILE;
import static org.eclipse.jgit.merge.ResolveMerger.T_INDEX;
import static org.eclipse.jgit.merge.ResolveMerger.T_OURS;
import static org.eclipse.jgit.merge.ResolveMerger.T_THEIRS;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class RenameResolver {

  public enum RenameConflict{NO_CONFLICT
  public enum RenameType{NO_RENAME

  public class RenameEntry {
    public RenameEntry(String sourcePath){
      this.sourcePath = sourcePath;
    }
    public String sourcePath;
    public String targetPath;
    public RenameType renameType = RenameType.NO_RENAME;
    public Map<Integer
  }
   Map of source rename paths (present in {@link ResolveMerger#T_BASE} to target rename paths by the corresponding side trees({@link ResolveMerger#T_OURS}
  public Map<String

  public Map<Integer


  public Map<String
  public Map<String

  private ObjectReader reader;
  private DiffConfig diffCfg;
  @Nullable
  private final Repository db;

  public RenameResolver(@Nullable Repository db
      RevTree head
    this.db = db;
    this.reader = reader;
    this.diffCfg = diffCfg;
    addRenames(baseTree
  }

  public void addRenames(@Nullable RevTree baseTree
        RevTree head
    if(baseTree == null){
      return;
    }
    RenameDetector renameDetector = new RenameDetector(reader
    List<DiffEntry> headRenames = computeRenames(renameDetector
    List<DiffEntry> mergeRenames = computeRenames(renameDetector
    for (DiffEntry entry : headRenames) {
      addRenameEntry(entry
    }
    for (DiffEntry entry : mergeRenames) {
      addRenameEntry(entry
    }

    detectRenameConflicts(headRenames

    renamePathsByTree.put(T_OURS
    renamePathsByTree.put(T_THEIRS
    for (Entry<String
      RenameEntry renameEntry = baseRename.getValue();
      for (Entry<Integer
        renamePathsByTree.get(sideRename.getKey())
            .put(sideRename.getValue()
      }
      renameEntry.renameType = getRenameType(renameEntry);
      renameEntry.targetPath = getTargetPath(renameEntry);
    }

  }

  private RenameType getRenameType(RenameEntry renameEntry){
    if(renameEntry.targetPaths.size() > 1) {
      return renameEntry.targetPaths.get(T_OURS)
          .equals(renameEntry.targetPaths.get(T_THEIRS)) ? RenameType.RENAME_BOTH_NO_CONFLICT : RenameType.RENAME_CONFLICT;
    } else if (renameEntry.targetPaths.size() == 1){
      return  renameEntry.targetPaths.containsKey(T_OURS)? RenameType.RENAME_IN_OURS: RenameType.RENAME_IN_THEIRS;
    } else {
      return RenameType.NO_RENAME;
    }
  }
  private String getTargetPath(RenameEntry renameEntry){
    if(renameEntry.renameType.equals(RenameType.NO_RENAME) || renameEntry.renameType.equals(RenameType.RENAME_CONFLICT)) {
      return null;
    } else {
      return renameEntry.targetPaths.values().stream().findAny().get();
    }
  }

  private List<DiffEntry> computeRenames(RenameDetector renameDetector
      RevTree otherTree)
      throws IOException {
    TreeWalk tw = new NameConflictTreeWalk(db
    tw.reset();
    tw.addTree(baseTree);
    tw.addTree(otherTree);
    tw.setFilter(TreeFilter.ANY_DIFF);

    renameDetector.reset();
    renameDetector.setBreakScore(100);
    renameDetector.addAll(DiffEntry.scan(tw
    return renameDetector.compute();
  }

  private void addRenameEntry(DiffEntry entry
    if (!entry.getChangeType().equals(ChangeType.RENAME) || entry.getOldPath()
        .equals(entry.getNewPath())) {
      return;
    }
    if (FileMode.TREE.equals(entry.getNewMode()) || FileMode.TREE.equals(entry.getOldMode())) {
      return;
    }
    if (!baseRenamePaths.containsKey(entry.getOldPath())) {
      baseRenamePaths.put(entry.getOldPath()
    }

    baseRenamePaths.get(entry.getOldPath()).targetPaths.put(entrySide
  }


  private void detectRenameConflicts(List<DiffEntry> headRenames
    Set<String> headDeletions = headRenames.stream().filter(x -> x.getChangeType().equals(
        ChangeType.DELETE)).map(x -> x.getOldPath()).collect(
        Collectors.toSet());
    Map<String
        x-> x));

    Set<String> mergeDeletions = mergeRenames.stream().filter(x -> x.getChangeType().equals(ChangeType.DELETE)).map(x -> x.getOldPath()).collect(
        Collectors.toSet());
    Map<String
        x-> x));
    Set<String> offSourceRenames = new HashSet<>();
    for (Entry<String
      Map<Integer
      if (renames.containsKey(T_OURS) && !renames.containsKey(T_THEIRS)) {
        offSourceRenames.addAll(
            detectRenameConflicts(baseRename.getKey()
                mergeDeletions));
      } else if (renames.containsKey(T_THEIRS) && !renames.containsKey(T_OURS)) {
        offSourceRenames.addAll(
            detectRenameConflicts(baseRename.getKey()
                headDeletions));
      } else if (!renames.get(T_OURS).equals(renames.get(T_THEIRS))) {
        if (mergeDiffByTarget.containsKey(baseRename.getKey()) && !headDiffByTarget.containsKey(
            baseRename.getKey())) {
          baseRename.getValue().targetPaths.remove(T_THEIRS);
        } else if (headDiffByTarget.containsKey(baseRename.getKey())
            && !mergeDiffByTarget.containsKey(baseRename.getKey())) {
          baseRename.getValue().targetPaths.remove(T_OURS);
        } else if (headDiffByTarget.containsKey(baseRename.getKey())
            && mergeDiffByTarget.containsKey(baseRename.getKey())) {
          offSourceRenames.add(baseRename.getKey());
        } else if (headDiffByTarget.containsKey(renames.get(T_THEIRS))
            || mergeDiffByTarget.containsKey(renames.get(T_OURS))) {
          offSourceRenames.add(baseRename.getKey());
        } else {
          recordSourceConflict(baseRename.getKey()
        }
      }
    }
    offSourceRenames.forEach(x -> baseRenamePaths.remove(x));

  }

  private Set<String> detectRenameConflicts(String sourcePath
    Set<String> offSourceRenames = new HashSet<>();
    if (otherSideDeletions.contains(sourcePath)) {
      recordSourceConflict(sourcePath
      recordTargetConflict(sideTargetPath
    }
    if(!otherSideDiffsByTargetPath.containsKey(sideTargetPath)){
      return Set.of();
    }

    DiffEntry otherSideTargetDiffEntry = otherSideDiffsByTargetPath.get(sideTargetPath);
    if (!otherSideTargetDiffEntry.getOldPath().equals(sourcePath)) {
      if(!otherSideDiffsByTargetPath.get(
          sideTargetPath).getChangeType().equals(ChangeType.RENAME)) {
        recordSourceConflict(sourcePath
        recordTargetConflict(sideTargetPath
        offSourceRenames.add(sourcePath);
      } else if(!otherSideTargetDiffEntry.getOldPath().equals(sideTargetPath)){
        recordSourceConflict(sourcePath
        recordSourceConflict(otherSideTargetDiffEntry.getOldPath()
        recordTargetConflict(sideTargetPath
        offSourceRenames.add(sourcePath);
        offSourceRenames.add(otherSideTargetDiffEntry.getOldPath());
      }
    }
    return offSourceRenames;
  }

  public void recordSourceConflict(String path
    if(!conflictingSourceRenamePath.containsKey(renameType)) {
      conflictingSourceRenamePath.put(path
    }
  }

  public void recordTargetConflict(String path
    if(!conflictingTargetRenamePath.containsKey(renameType)) {
      conflictingTargetRenamePath.put(path
    }
  }

  public boolean isBaseRename(AbstractTreeIterator base) {
    return base != null && baseRenamePaths.containsKey(base.getEntryPathString());
  }

  public boolean isBaseRename(String path) {
    return baseRenamePaths.containsKey(path);
  }

  public boolean isRenameFromBase(int nthA
    return side != null && isRenameFromBase(nthA
  }

  public boolean isRenameFromBase(int nthA
    return (renamePathsByTree.containsKey(nthA) && renamePathsByTree.get(nthA)
        .containsKey(path));
  }

  public boolean isRenameEntry(AbstractTreeIterator base
      AbstractTreeIterator ours
    return isBaseRename(base) || isRenameFromBase(T_OURS
        theirs);
  }

  public RenameType getRenameType(AbstractTreeIterator base){
    if(base == null || !isBaseRename(base)){
      return RenameType.NO_RENAME;
    }
    return baseRenamePaths.get(base.getEntryPathString()).renameType;
  }
  public RenameEntry getRenameEntry(AbstractTreeIterator base){
    return baseRenamePaths.get(base.getEntryPathString());
  }

  public String getRenameTarget(String sourcePath){
    return baseRenamePaths.get(sourcePath).targetPath;
  }
}
