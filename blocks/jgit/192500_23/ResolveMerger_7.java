package org.eclipse.jgit.merge;

import static org.eclipse.jgit.merge.ResolveMerger.T_OURS;
import static org.eclipse.jgit.merge.ResolveMerger.T_THEIRS;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.NameConflictTreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class RenameResolver {

	public enum RenameType {
		NO_RENAME
		RENAME_IN_OURS
		RENAME_IN_THEIRS
		RENAME_BOTH_NO_CONFLICT
		RENAME_CONFLICT
	}

	public enum RenameConflict {
		NO_CONFLICT
		RENAME_BOTH_SIDES_CONFLICT
		RENAME_ADD_CONFLICT
		RENAME_DELETE_CONFLICT
		MULTIPLE_RENAME_CONFLICT
	}

	public static class RenameEntry {
		private RenameEntry(String sourcePath) {
			this.sourcePath = sourcePath;
		}

		private String sourcePath;

		private String targetPath;

		private RenameType renameType = RenameType.NO_RENAME;

		private RenameConflict renameConflict = RenameConflict.NO_CONFLICT;

		private Map<Integer

		public String getSourcePath() {
			return sourcePath;
		}

		public String getTargetPath() {
			return targetPath;
		}

		public String getTargetPath(int nth) {
			return targetPaths.get(nth);
		}

		public Collection<String> getTargetPaths() {
			return targetPaths.values();
		}

		public RenameType getRenameType() {
			return renameType;
		}

		public RenameConflict getRenameConflict() {
			return renameConflict;
		}
	}

	private Map<String

	private Map<Integer

	private Map<String

	private ObjectReader reader;

	private DiffConfig diffCfg;

	@Nullable
	private final Repository db;

	public RenameResolver(@Nullable Repository db
			Config config
			RevTree mergeTree) throws IOException {
		this.db = db;
		this.reader = reader;
		this.diffCfg = config.get(DiffConfig.KEY);
		addRenames(baseTree
	}

	private void addRenames(@Nullable RevTree baseTree
			RevTree merge) throws IOException {
		if (baseTree == null) {
			return;
		}
		RenameDetector renameDetector = new RenameDetector(reader
		List<DiffEntry> headRenames = computeRenames(renameDetector
				head);
		List<DiffEntry> mergeRenames = computeRenames(renameDetector
				merge);
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
				.entrySet()) {
			RenameEntry renameEntry = baseRename.getValue();
			for (Entry<Integer
					.entrySet()) {
				renamePathsByTree.get(sideRename.getKey())
						.put(sideRename.getValue()
			}
			renameEntry.renameType = assignRenameType(renameEntry);
			renameEntry.targetPath = assignTargetPath(renameEntry);
		}

	}

	private RenameType assignRenameType(RenameEntry renameEntry) {
		if (renameEntry.targetPaths.size() > 1) {
			return renameEntry.targetPaths.get(T_OURS)
					.equals(renameEntry.targetPaths.get(T_THEIRS))
							? RenameType.RENAME_BOTH_NO_CONFLICT
							: RenameType.RENAME_CONFLICT;
		} else if (renameEntry.targetPaths.size() == 1) {
			return renameEntry.targetPaths.containsKey(T_OURS)
					? RenameType.RENAME_IN_OURS
					: RenameType.RENAME_IN_THEIRS;
		} else {
			return RenameType.NO_RENAME;
		}
	}

	private String assignTargetPath(RenameEntry renameEntry) {
		if (renameEntry.renameType.equals(RenameType.NO_RENAME)
				|| renameEntry.renameType.equals(RenameType.RENAME_CONFLICT)) {
			return null;
		} else {
			return renameEntry.targetPaths.values().stream().findAny().get();
		}
	}

	private List<DiffEntry> computeRenames(RenameDetector renameDetector
			RevTree baseTree
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
		if (!entry.getChangeType().equals(ChangeType.RENAME)
				|| entry.getOldPath().equals(entry.getNewPath())) {
			return;
		}
		if (FileMode.TREE.equals(entry.getNewMode())
				|| FileMode.TREE.equals(entry.getOldMode())) {
			return;
		}
		if (!baseRenamePaths.containsKey(entry.getOldPath())) {
			baseRenamePaths.put(entry.getOldPath()
					new RenameEntry(entry.getOldPath()));
		}

		baseRenamePaths.get(entry.getOldPath()).targetPaths.put(entrySide
				entry.getNewPath());
	}

	private void detectRenameConflicts(List<DiffEntry> headRenames
			List<DiffEntry> mergeRenames) {
		Set<String> headDeletions = headRenames.stream()
				.filter(x -> x.getChangeType().equals(ChangeType.DELETE))
				.map(x -> x.getOldPath()).collect(Collectors.toSet());
		Map<String
				.filter(x -> !x.getChangeType().equals(ChangeType.DELETE))
				.collect(Collectors.toMap(x -> x.getNewPath()

		Set<String> mergeDeletions = mergeRenames.stream()
				.filter(x -> x.getChangeType().equals(ChangeType.DELETE))
				.map(x -> x.getOldPath()).collect(Collectors.toSet());
		Map<String
				.filter(x -> !x.getChangeType().equals(ChangeType.DELETE))
				.collect(Collectors.toMap(x -> x.getNewPath()
		Set<String> offSourceRenames = new HashSet<>();
		for (Entry<String
				.entrySet()) {
			Map<Integer
			if (renames.containsKey(T_OURS) && !renames.containsKey(T_THEIRS)) {
				offSourceRenames.addAll(detectRenameConflicts(
						baseRename.getKey()
						mergeDiffByTarget
			} else if (renames.containsKey(T_THEIRS)
					&& !renames.containsKey(T_OURS)) {
				offSourceRenames.addAll(detectRenameConflicts(
						baseRename.getKey()
						headDiffByTarget
			} else if (!renames.get(T_OURS).equals(renames.get(T_THEIRS))) {
				if (mergeDiffByTarget.containsKey(baseRename.getKey())
						&& !headDiffByTarget.containsKey(baseRename.getKey())) {
					baseRename.getValue().targetPaths.remove(T_THEIRS);
				} else if (headDiffByTarget.containsKey(baseRename.getKey())
						&& !mergeDiffByTarget
								.containsKey(baseRename.getKey())) {
					baseRename.getValue().targetPaths.remove(T_OURS);
				} else if (headDiffByTarget.containsKey(baseRename.getKey())
						&& mergeDiffByTarget.containsKey(baseRename.getKey())) {
					offSourceRenames.add(baseRename.getKey());
				} else if (headDiffByTarget.containsKey(renames.get(T_THEIRS))
						|| mergeDiffByTarget.containsKey(renames.get(T_OURS))) {
					offSourceRenames.add(baseRename.getKey());
				} else {
					recordSourceConflict(baseRename.getKey()
							RenameConflict.RENAME_BOTH_SIDES_CONFLICT);
				}
			}
		}
		offSourceRenames.forEach(x -> baseRenamePaths.remove(x));

	}

	private Set<String> detectRenameConflicts(String sourcePath
			String targetPath
			Map<String
			Set<String> otherSideDeletions) {
		Set<String> offSourceRenames = new HashSet<>();
		if (otherSideDeletions.contains(sourcePath)) {
			recordSourceConflict(sourcePath
					RenameConflict.RENAME_DELETE_CONFLICT);
		}

		if (!otherSideDiffsByTargetPath.containsKey(targetPath)) {
			return Set.of();
		}

		DiffEntry otherSideTargetDiffEntry = otherSideDiffsByTargetPath
				.get(targetPath);
		if (!otherSideTargetDiffEntry.getOldPath().equals(sourcePath)) {
			if (!otherSideTargetDiffEntry.getChangeType()
					.equals(ChangeType.RENAME)) {
				recordSourceConflict(sourcePath
						RenameConflict.RENAME_ADD_CONFLICT);
				offSourceRenames.add(sourcePath);
			} else if (!otherSideTargetDiffEntry.getOldPath()
					.equals(targetPath)) {
				recordSourceConflict(sourcePath
						RenameConflict.MULTIPLE_RENAME_CONFLICT);
				recordSourceConflict(otherSideTargetDiffEntry.getOldPath()
						RenameConflict.MULTIPLE_RENAME_CONFLICT);
				offSourceRenames.add(sourcePath);
				offSourceRenames.add(otherSideTargetDiffEntry.getOldPath());
			}
		}
		return offSourceRenames;
	}

	private void recordSourceConflict(String path
			RenameConflict renameConflict) {
		if (!conflictingRenames.containsKey(renameConflict)) {
			RenameEntry entry = baseRenamePaths.get(path);
			entry.renameConflict = renameConflict;
			conflictingRenames.put(path
		}
	}

	public boolean isSourceOfRename(AbstractTreeIterator base) {
		return base != null
				&& baseRenamePaths.containsKey(base.getEntryPathString());
	}

	public boolean isSourceOfRename(String path) {
		return baseRenamePaths.containsKey(path);
	}

	public boolean isTargetOfRename(int nth
		return side != null && isTargetOfRename(nth
	}

	public boolean isTargetOfRename(int nth
		return (renamePathsByTree.containsKey(nth)
				&& renamePathsByTree.get(nth).containsKey(path));
	}

	public boolean isTargetRename(String path) {
		return renamePathsByTree.get(T_OURS).containsKey(path)
				|| renamePathsByTree.get(T_THEIRS).containsKey(path);
	}

	public boolean isRenameEntry(AbstractTreeIterator base
			AbstractTreeIterator ours
		return isSourceOfRename(base) || isTargetOfRename(T_OURS
				|| isTargetOfRename(T_THEIRS
	}

	public RenameType getRenameType(String sourcePath) {
		if (!isSourceOfRename(sourcePath)) {
			return RenameType.NO_RENAME;
		}
		return baseRenamePaths.get(sourcePath).renameType;
	}

	public RenameEntry getRenameEntry(String sourcePath) {
		return baseRenamePaths.get(sourcePath);
	}

	public RenameEntry getRenameConflict(String sourcePath) {
		return conflictingRenames.get(sourcePath);
	}

	public String getRenameTarget(String sourcePath) {
		return baseRenamePaths.get(sourcePath).targetPath;
	}

	public Set<Entry<String
		return conflictingRenames.entrySet();
	}

	public Set<Entry<String
		return this.baseRenamePaths.entrySet().stream()
				.filter(x -> !conflictingRenames.containsKey(x.getKey()))
				.collect(Collectors.toSet());
	}
}
