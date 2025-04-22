
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk.IgnoreSubmoduleMode;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.IndexDiffFilter;
import org.eclipse.jgit.treewalk.filter.SkipWorkTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class IndexDiff {

	public static enum StageState {
		BOTH_DELETED(1)

		ADDED_BY_US(2)

		DELETED_BY_THEM(3)

		ADDED_BY_THEM(4)

		DELETED_BY_US(5)

		BOTH_ADDED(6)

		BOTH_MODIFIED(7);

		private final int stageMask;

		private StageState(int stageMask) {
			this.stageMask = stageMask;
		}

		int getStageMask() {
			return stageMask;
		}

		public boolean hasBase() {
			return (stageMask & 1) != 0;
		}

		public boolean hasOurs() {
			return (stageMask & 2) != 0;
		}

		public boolean hasTheirs() {
			return (stageMask & 4) != 0;
		}

		static StageState fromMask(int stageMask) {
			switch (stageMask) {
				return BOTH_DELETED;
				return ADDED_BY_US;
				return DELETED_BY_THEM;
				return ADDED_BY_THEM;
				return DELETED_BY_US;
				return BOTH_ADDED;
				return BOTH_MODIFIED;
			default:
				return null;
			}
		}
	}

	private static final class ProgressReportingFilter extends TreeFilter {

		private final ProgressMonitor monitor;

		private int count = 0;

		private int stepSize;

		private final int total;

		private ProgressReportingFilter(ProgressMonitor monitor
			this.monitor = monitor;
			this.total = total;
			stepSize = total / 100;
			if (stepSize == 0)
				stepSize = 1000;
		}

		@Override
		public boolean shouldBeRecursive() {
			return false;
		}

		@Override
		public boolean include(TreeWalk walker)
				throws MissingObjectException
				IncorrectObjectTypeException
			count++;
			if (count % stepSize == 0) {
				if (count <= total)
					monitor.update(stepSize);
				if (monitor.isCancelled())
					throw StopWalkException.INSTANCE;
			}
			return true;
		}

		@Override
		public TreeFilter clone() {
			throw new IllegalStateException(
							+ getClass().getName());
		}
	}

	private final static int TREE = 0;

	private final static int INDEX = 1;

	private final static int WORKDIR = 2;

	private final Repository repository;

	private final AnyObjectId tree;

	private TreeFilter filter = null;

	private final WorkingTreeIterator initialWorkingTreeIterator;

	private Set<String> added = new HashSet<>();

	private Set<String> changed = new HashSet<>();

	private Set<String> removed = new HashSet<>();

	private Set<String> missing = new HashSet<>();

	private Set<String> modified = new HashSet<>();

	private Set<String> untracked = new HashSet<>();

	private Map<String

	private Set<String> ignored;

	private Set<String> assumeUnchanged;

	private DirCache dirCache;

	private IndexDiffFilter indexDiffFilter;

	private Map<String

	private IgnoreSubmoduleMode ignoreSubmoduleMode = null;

	private Map<FileMode

	public IndexDiff(Repository repository
			WorkingTreeIterator workingTreeIterator) throws IOException {
		this(repository
	}

	public IndexDiff(Repository repository
			WorkingTreeIterator workingTreeIterator) throws IOException {
		this.repository = repository;
		if (objectId != null) {
			try (RevWalk rw = new RevWalk(repository)) {
				tree = rw.parseTree(objectId);
			}
		} else {
			tree = null;
		}
		this.initialWorkingTreeIterator = workingTreeIterator;
	}

	public void setIgnoreSubmoduleMode(IgnoreSubmoduleMode mode) {
		this.ignoreSubmoduleMode = mode;
	}

	public interface WorkingTreeIteratorFactory {
		public WorkingTreeIterator getWorkingTreeIterator(Repository repo);
	}

	private WorkingTreeIteratorFactory wTreeIt = new WorkingTreeIteratorFactory() {
		@Override
		public WorkingTreeIterator getWorkingTreeIterator(Repository repo) {
			return new FileTreeIterator(repo);
		}
	};

	public void setWorkingTreeItFactory(WorkingTreeIteratorFactory wTreeIt) {
		this.wTreeIt = wTreeIt;
	}

	public void setFilter(TreeFilter filter) {
		this.filter = filter;
	}

	public boolean diff() throws IOException {
		return diff(null
	}

	public boolean diff(final ProgressMonitor monitor
			int estIndexSize
			throws IOException {
		dirCache = repository.readDirCache();

		try (TreeWalk treeWalk = new TreeWalk(repository)) {
			treeWalk.setOperationType(OperationType.CHECKIN_OP);
			treeWalk.setRecursive(true);
			if (tree != null)
				treeWalk.addTree(tree);
			else
				treeWalk.addTree(new EmptyTreeIterator());
			treeWalk.addTree(new DirCacheIterator(dirCache));
			treeWalk.addTree(initialWorkingTreeIterator);
			initialWorkingTreeIterator.setDirCacheIterator(treeWalk
			Collection<TreeFilter> filters = new ArrayList<>(4);

			if (monitor != null) {
				if (estIndexSize == 0)
					estIndexSize = dirCache.getEntryCount();
				int total = Math.max(estIndexSize * 10 / 9
						estWorkTreeSize * 10 / 9);
				monitor.beginTask(title
				filters.add(new ProgressReportingFilter(monitor
			}

			if (filter != null)
				filters.add(filter);
			filters.add(new SkipWorkTreeFilter(INDEX));
			indexDiffFilter = new IndexDiffFilter(INDEX
			filters.add(indexDiffFilter);
			treeWalk.setFilter(AndTreeFilter.create(filters));
			fileModes.clear();
			while (treeWalk.next()) {
				AbstractTreeIterator treeIterator = treeWalk.getTree(TREE
						AbstractTreeIterator.class);
				DirCacheIterator dirCacheIterator = treeWalk.getTree(INDEX
						DirCacheIterator.class);
				WorkingTreeIterator workingTreeIterator = treeWalk
						.getTree(WORKDIR

				if (dirCacheIterator != null) {
					final DirCacheEntry dirCacheEntry = dirCacheIterator
							.getDirCacheEntry();
					if (dirCacheEntry != null) {
						int stage = dirCacheEntry.getStage();
						if (stage > 0) {
							String path = treeWalk.getPathString();
							addConflict(path
							continue;
						}
					}
				}

				if (treeIterator != null) {
					if (dirCacheIterator != null) {
						if (!treeIterator.idEqual(dirCacheIterator)
								|| treeIterator
										.getEntryRawMode() != dirCacheIterator
												.getEntryRawMode()) {
							if (!isEntryGitLink(treeIterator)
									|| !isEntryGitLink(dirCacheIterator)
									|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
								changed.add(treeWalk.getPathString());
						}
					} else {
						if (!isEntryGitLink(treeIterator)
								|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
							removed.add(treeWalk.getPathString());
						if (workingTreeIterator != null)
							untracked.add(treeWalk.getPathString());
					}
				} else {
					if (dirCacheIterator != null) {
						if (!isEntryGitLink(dirCacheIterator)
								|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
							added.add(treeWalk.getPathString());
					} else {
						if (workingTreeIterator != null
								&& !workingTreeIterator.isEntryIgnored()) {
							untracked.add(treeWalk.getPathString());
						}
					}
				}

				if (dirCacheIterator != null) {
					if (workingTreeIterator == null) {
						if (!isEntryGitLink(dirCacheIterator)
								|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
							missing.add(treeWalk.getPathString());
					} else {
						if (workingTreeIterator.isModified(
								dirCacheIterator.getDirCacheEntry()
								treeWalk.getObjectReader())) {
							if (!isEntryGitLink(dirCacheIterator)
									|| !isEntryGitLink(workingTreeIterator)
									|| (ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL
											&& ignoreSubmoduleMode != IgnoreSubmoduleMode.DIRTY))
								modified.add(treeWalk.getPathString());
						}
					}
				}

				String path = treeWalk.getPathString();
				if (path != null) {
					for (int i = 0; i < treeWalk.getTreeCount(); i++) {
						recordFileMode(path
					}
				}
			}
		}

		if (ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL) {
			IgnoreSubmoduleMode localIgnoreSubmoduleMode = ignoreSubmoduleMode;
			SubmoduleWalk smw = SubmoduleWalk.forIndex(repository);
			while (smw.next()) {
				try {
					if (localIgnoreSubmoduleMode == null)
						localIgnoreSubmoduleMode = smw.getModulesIgnore();
					if (IgnoreSubmoduleMode.ALL
							.equals(localIgnoreSubmoduleMode))
						continue;
				} catch (ConfigInvalidException e) {
					throw new IOException(MessageFormat.format(
							JGitText.get().invalidIgnoreParamSubmodule
							smw.getPath())
				}
				try (Repository subRepo = smw.getRepository()) {
					if (subRepo != null) {
						String subRepoPath = smw.getPath();
						if (subHead != null
								&& !subHead.equals(smw.getObjectId())) {
							modified.add(subRepoPath);
							recordFileMode(subRepoPath
						} else if (ignoreSubmoduleMode != IgnoreSubmoduleMode.DIRTY) {
							IndexDiff smid = submoduleIndexDiffs.get(smw
									.getPath());
							if (smid == null) {
								smid = new IndexDiff(subRepo
										smw.getObjectId()
										wTreeIt.getWorkingTreeIterator(subRepo));
								submoduleIndexDiffs.put(subRepoPath
							}
							if (smid.diff()) {
								if (ignoreSubmoduleMode == IgnoreSubmoduleMode.UNTRACKED
										&& smid.getAdded().isEmpty()
										&& smid.getChanged().isEmpty()
										&& smid.getConflicting().isEmpty()
										&& smid.getMissing().isEmpty()
										&& smid.getModified().isEmpty()
										&& smid.getRemoved().isEmpty()) {
									continue;
								}
								modified.add(subRepoPath);
								recordFileMode(subRepoPath
							}
						}
					}
				}
			}

		}

		if (monitor != null)
			monitor.endTask();

		ignored = indexDiffFilter.getIgnoredPaths();
		if (added.isEmpty() && changed.isEmpty() && removed.isEmpty()
				&& missing.isEmpty() && modified.isEmpty()
				&& untracked.isEmpty())
			return false;
		else
			return true;
	}

	private void recordFileMode(String path
		Set<String> values = fileModes.get(mode);
		if (path != null) {
			if (values == null) {
				values = new HashSet<>();
				fileModes.put(mode
			}
			values.add(path);
		}
	}

	private boolean isEntryGitLink(AbstractTreeIterator ti) {
		return ((ti != null) && (ti.getEntryRawMode() == FileMode.GITLINK
				.getBits()));
	}

	private void addConflict(String path
		StageState existingStageStates = conflicts.get(path);
		byte stageMask = 0;
		if (existingStageStates != null)
			stageMask |= existingStageStates.getStageMask();
		int shifts = stage - 1;
		stageMask |= (1 << shifts);
		StageState stageState = StageState.fromMask(stageMask);
		conflicts.put(path
	}

	public Set<String> getAdded() {
		return added;
	}

	public Set<String> getChanged() {
		return changed;
	}

	public Set<String> getRemoved() {
		return removed;
	}

	public Set<String> getMissing() {
		return missing;
	}

	public Set<String> getModified() {
		return modified;
	}

	public Set<String> getUntracked() {
		return untracked;
	}

	public Set<String> getConflicting() {
		return conflicts.keySet();
	}

	public Map<String
		return conflicts;
	}

	public Set<String> getIgnoredNotInIndex() {
		return ignored;
	}

	public Set<String> getAssumeUnchanged() {
		if (assumeUnchanged == null) {
			HashSet<String> unchanged = new HashSet<>();
			for (int i = 0; i < dirCache.getEntryCount(); i++)
				if (dirCache.getEntry(i).isAssumeValid())
					unchanged.add(dirCache.getEntry(i).getPathString());
			assumeUnchanged = unchanged;
		}
		return assumeUnchanged;
	}

	public Set<String> getUntrackedFolders() {
		return ((indexDiffFilter == null) ? Collections.<String> emptySet()
				: new HashSet<>(indexDiffFilter.getUntrackedFolders()));
	}

	public FileMode getIndexMode(String path) {
		final DirCacheEntry entry = dirCache.getEntry(path);
		return entry != null ? entry.getFileMode() : FileMode.MISSING;
	}

	public Set<String> getPathsWithIndexMode(FileMode mode) {
		Set<String> paths = fileModes.get(mode);
		if (paths == null)
			paths = new HashSet<>();
		return paths;
	}
}
