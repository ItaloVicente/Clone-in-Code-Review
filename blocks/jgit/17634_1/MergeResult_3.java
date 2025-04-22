package org.eclipse.jgit.merge;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;

public class MergeDriverResult {
	private final Status status;

	private final boolean pruneSubtree;

	private final Set<String> paths;

	private final MergeFailureReason failureReason;

	private final Map<String

	private final Map<String

	private final Set<String> toBeDeleted;

	private final Set<String> modifiedFiles;

	public MergeDriverResult(Status status
		this(status
				.<String
				Collections.<String
						.<String> emptySet()
	}

	public MergeDriverResult(Status status
			Set<String> paths
		this(status
				.<String
				Collections.<String
						.<String> emptySet()
	}

	public MergeDriverResult(Status status
			Set<String> paths
			Map<String
			Map<String
			Set<String> modifiedFiles) {
		this.status = status;
		this.pruneSubtree = pruneSubtree;
		this.paths = paths;
		this.failureReason = reason;
		this.lowLevelResults = lowLevelResults;
		this.toBeCheckedOut = toBeCheckedOut;
		this.toBeDeleted = toBeDeleted;
		this.modifiedFiles = modifiedFiles;
	}

	public boolean shouldPruneSubtree() {
		return pruneSubtree;
	}

	public Status getStatus() {
		return status;
	}

	public Set<String> getPaths() {
		return paths;
	}

	public MergeFailureReason getFailureReason() {
		return failureReason;
	}

	public Map<String
		return lowLevelResults;
	}

	public Map<String
		return toBeCheckedOut;
	}

	public Set<String> getToBeDeleted() {
		return toBeDeleted;
	}

	public Set<String> getModifiedFiles() {
		return modifiedFiles;
	}

	public static enum Status {
		FAILED

		OK

		CONFLICT;
	}
}
