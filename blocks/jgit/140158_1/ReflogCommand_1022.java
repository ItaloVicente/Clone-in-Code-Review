package org.eclipse.jgit.api;

import java.util.List;
import java.util.Map;

import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;

public class RebaseResult {
	public enum Status {
		OK {
			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		ABORTED {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		STOPPED {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		EDIT {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		FAILED {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		UNCOMMITTED_CHANGES {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		CONFLICTS {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		UP_TO_DATE {
			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		FAST_FORWARD {
			@Override
			public boolean isSuccessful() {
				return true;
			}
		}

		NOTHING_TO_COMMIT {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}

		INTERACTIVE_PREPARED {
			@Override
			public boolean isSuccessful() {
				return false;
			}
		}

		STASH_APPLY_CONFLICTS {
			@Override
			public boolean isSuccessful() {
				return true;
			}
		};

		public abstract boolean isSuccessful();
	}

	static final RebaseResult OK_RESULT = new RebaseResult(Status.OK);

	static final RebaseResult ABORTED_RESULT = new RebaseResult(Status.ABORTED);

	static final RebaseResult UP_TO_DATE_RESULT = new RebaseResult(
			Status.UP_TO_DATE);

	static final RebaseResult FAST_FORWARD_RESULT = new RebaseResult(
			Status.FAST_FORWARD);

	static final RebaseResult NOTHING_TO_COMMIT_RESULT = new RebaseResult(
			Status.NOTHING_TO_COMMIT);

	static final RebaseResult INTERACTIVE_PREPARED_RESULT =  new RebaseResult(
			Status.INTERACTIVE_PREPARED);

	static final RebaseResult STASH_APPLY_CONFLICTS_RESULT = new RebaseResult(
			Status.STASH_APPLY_CONFLICTS);

	private final Status status;

	private final RevCommit currentCommit;

	private Map<String

	private List<String> conflicts;

	private List<String> uncommittedChanges;

	private RebaseResult(Status status) {
		this.status = status;
		currentCommit = null;
	}

	private RebaseResult(Status status
		this.status = status;
		currentCommit = commit;
	}

	static RebaseResult result(RebaseResult.Status status
			RevCommit commit) {
		return new RebaseResult(status
	}

	static RebaseResult failed(
			Map<String
		RebaseResult result = new RebaseResult(Status.FAILED);
		result.failingPaths = failingPaths;
		return result;
	}

	static RebaseResult conflicts(List<String> conflicts) {
		RebaseResult result = new RebaseResult(Status.CONFLICTS);
		result.conflicts = conflicts;
		return result;
	}

	static RebaseResult uncommittedChanges(List<String> uncommittedChanges) {
		RebaseResult result = new RebaseResult(Status.UNCOMMITTED_CHANGES);
		result.uncommittedChanges = uncommittedChanges;
		return result;
	}

	public Status getStatus() {
		return status;
	}

	public RevCommit getCurrentCommit() {
		return currentCommit;
	}

	public Map<String
		return failingPaths;
	}

	public List<String> getConflicts() {
		return conflicts;
	}

	public List<String> getUncommittedChanges() {
		return uncommittedChanges;
	}

}
