package org.eclipse.jgit.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.lib.IndexDiff.StageState;

public class Status {
	private final IndexDiff diff;

	private final boolean clean;

	private final boolean hasUncommittedChanges;

	public Status(IndexDiff diff) {
		super();
		this.diff = diff;
				|| !diff.getConflicting().isEmpty();
				&& diff.getUntracked().isEmpty();
	}

	public boolean isClean() {
		return clean;
	}

	public boolean hasUncommittedChanges() {
		return hasUncommittedChanges;
	}

	public Set<String> getAdded() {
		return Collections.unmodifiableSet(diff.getAdded());
	}

	public Set<String> getChanged() {
		return Collections.unmodifiableSet(diff.getChanged());
	}

	public Set<String> getRemoved() {
		return Collections.unmodifiableSet(diff.getRemoved());
	}

	public Set<String> getMissing() {
		return Collections.unmodifiableSet(diff.getMissing());
	}

	public Set<String> getModified() {
		return Collections.unmodifiableSet(diff.getModified());
	}

	public Set<String> getUntracked() {
		return Collections.unmodifiableSet(diff.getUntracked());
	}

	public Set<String> getUntrackedFolders() {
		return Collections.unmodifiableSet(diff.getUntrackedFolders());
	}

	public Set<String> getConflicting() {
		return Collections.unmodifiableSet(diff.getConflicting());
	}

	public Map<String
		return Collections.unmodifiableMap(diff.getConflictingStageStates());
	}

	public Set<String> getIgnoredNotInIndex() {
		return Collections.unmodifiableSet(diff.getIgnoredNotInIndex());
	}

	public Set<String> getUncommittedChanges() {
		Set<String> uncommittedChanges = new HashSet<>();
		uncommittedChanges.addAll(diff.getAdded());
		uncommittedChanges.addAll(diff.getChanged());
		uncommittedChanges.addAll(diff.getRemoved());
		uncommittedChanges.addAll(diff.getMissing());
		uncommittedChanges.addAll(diff.getModified());
		uncommittedChanges.addAll(diff.getConflicting());
		return uncommittedChanges;
	}
}
