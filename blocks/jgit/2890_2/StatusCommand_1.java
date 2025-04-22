package org.eclipse.jgit.api;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jgit.lib.IndexDiff;

public class Status {
	private IndexDiff diff;

	public Status(IndexDiff diff) {
		super();
		this.diff = diff;
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
}
