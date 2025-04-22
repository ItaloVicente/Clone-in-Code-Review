package org.eclipse.egit.core.internal.indexdiff;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jgit.lib.IndexDiff;

public class IndexDiffData {

	private static final String NEW_LINE = "\n"; //$NON-NLS-1$

	private final Set<String> added;

	private final Set<String> changed;

	private final Set<String> removed;

	private final Set<String> missing;

	private final Set<String> modified;

	private final Set<String> untracked;

	private final Set<String> conflicts;

	private final Collection<IFile> changedFileResources;

	public IndexDiffData(IndexDiff indexDiff) {
		added = Collections.unmodifiableSet(new HashSet<String>(indexDiff
				.getAdded()));
		changed = Collections.unmodifiableSet(new HashSet<String>(indexDiff
				.getChanged()));
		removed = Collections.unmodifiableSet(new HashSet<String>(indexDiff
				.getRemoved()));
		missing = Collections.unmodifiableSet(new HashSet<String>(indexDiff
				.getMissing()));
		modified = Collections.unmodifiableSet(new HashSet<String>(indexDiff
				.getModified()));
		untracked = Collections.unmodifiableSet(new HashSet<String>(indexDiff
				.getUntracked()));
		conflicts = Collections.unmodifiableSet(new HashSet<String>(indexDiff
				.getConflicting()));
		changedFileResources = null;
	}

	public IndexDiffData(IndexDiffData baseDiff,
			Collection<String> changedFiles,
			Collection<IFile> changedFileResources,
			IndexDiff diffForChangedFiles) {
		this.changedFileResources = Collections
				.unmodifiableCollection(new HashSet<IFile>(changedFileResources));
		Set<String> added2 = new HashSet<String>(baseDiff.getAdded());
		Set<String> changed2 = new HashSet<String>(baseDiff.getChanged());
		Set<String> removed2 = new HashSet<String>(baseDiff.getRemoved());
		Set<String> missing2 = new HashSet<String>(baseDiff.getMissing());
		Set<String> modified2 = new HashSet<String>(baseDiff.getModified());
		Set<String> untracked2 = new HashSet<String>(baseDiff.getUntracked());
		Set<String> conflicts2 = new HashSet<String>(baseDiff.getConflicting());

		mergeList(added2, changedFiles, diffForChangedFiles.getAdded());
		mergeList(changed2, changedFiles, diffForChangedFiles.getChanged());
		mergeList(removed2, changedFiles, diffForChangedFiles.getRemoved());
		mergeList(missing2, changedFiles, diffForChangedFiles.getMissing());
		mergeList(modified2, changedFiles, diffForChangedFiles.getModified());
		mergeList(untracked2, changedFiles, diffForChangedFiles.getUntracked());
		mergeList(conflicts2, changedFiles,
				diffForChangedFiles.getConflicting());

		added = Collections.unmodifiableSet(added2);
		changed = Collections.unmodifiableSet(changed2);
		removed = Collections.unmodifiableSet(removed2);
		missing = Collections.unmodifiableSet(missing2);
		modified = Collections.unmodifiableSet(modified2);
		untracked = Collections.unmodifiableSet(untracked2);
		conflicts = Collections.unmodifiableSet(conflicts2);
	}

	private void mergeList(Set<String> baseList,
			Collection<String> changedFiles, Set<String> listForChangedFiles) {
		for (String file : changedFiles) {
			if (baseList.contains(file)) {
				if (!listForChangedFiles.contains(file))
					baseList.remove(file);
			} else {
				if (listForChangedFiles.contains(file))
					baseList.add(file);
			}
		}
	}

	public Set<String> getAdded() {
		return Collections.unmodifiableSet(added);
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
		return conflicts;
	}

	public Collection<IFile> getChangedFileResources() {
		return changedFileResources;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		dumpList(builder, "added", added); //$NON-NLS-1$
		dumpList(builder, "changed", changed); //$NON-NLS-1$
		dumpList(builder, "removed", removed); //$NON-NLS-1$
		dumpList(builder, "missing", missing); //$NON-NLS-1$
		dumpList(builder, "modified", modified); //$NON-NLS-1$
		dumpList(builder, "untracked", untracked); //$NON-NLS-1$
		dumpList(builder, "conflicts", conflicts); //$NON-NLS-1$
		dumpFileResourceList(builder,
				"changedFileResources", changedFileResources); //$NON-NLS-1$
		return builder.toString();
	}

	private void dumpList(StringBuilder builder, String listName,
			Set<String> list) {
		builder.append(listName);
		builder.append(NEW_LINE);
		for (String entry : list) {
			builder.append(entry);
			builder.append(NEW_LINE);
		}
		builder.append(NEW_LINE);
	}

	private void dumpFileResourceList(StringBuilder builder, String listName,
			Collection<IFile> list) {
		if (list == null)
			return;
		builder.append(listName);
		builder.append(NEW_LINE);
		for (IFile file : list) {
			builder.append(file.getFullPath().toOSString());
			builder.append(NEW_LINE);
		}
		builder.append(NEW_LINE);
	}

}
