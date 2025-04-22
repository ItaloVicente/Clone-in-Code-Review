package org.eclipse.jgit.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.merge.MergeChunk;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;

public class MergeResult {

	public enum MergeStatus {
		FAST_FORWARD {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		FAST_FORWARD_SQUASHED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		ALREADY_UP_TO_DATE {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		FAILED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		MERGED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		MERGED_SQUASHED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		MERGED_SQUASHED_NOT_COMMITTED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		CONFLICTING {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		ABORTED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		MERGED_NOT_COMMITTED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return true;
			}
		}
		NOT_SUPPORTED {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		}
		CHECKOUT_CONFLICT {
			@Override
			public String toString() {
			}

			@Override
			public boolean isSuccessful() {
				return false;
			}
		};

		public abstract boolean isSuccessful();
	}

	private ObjectId[] mergedCommits;

	private ObjectId base;

	private ObjectId newHead;

	private Map<String

	private MergeStatus mergeStatus;

	private String description;

	private MergeStrategy mergeStrategy;

	private Map<String

	private List<String> checkoutConflicts;

	public MergeResult(ObjectId newHead
			ObjectId[] mergedCommits
			MergeStrategy mergeStrategy
			Map<String
		this(newHead
				lowLevelResults
	}

	public MergeResult(ObjectId newHead
			ObjectId[] mergedCommits
			MergeStrategy mergeStrategy
			Map<String
			String description) {
		this(newHead
				lowLevelResults
	}

	public MergeResult(ObjectId newHead
			ObjectId[] mergedCommits
			MergeStrategy mergeStrategy
			Map<String
			Map<String
		this.newHead = newHead;
		this.mergedCommits = mergedCommits;
		this.base = base;
		this.mergeStatus = mergeStatus;
		this.mergeStrategy = mergeStrategy;
		this.description = description;
		this.failingPaths = failingPaths;
		if (lowLevelResults != null)
			for (Map.Entry<String
					.entrySet())
				addConflict(result.getKey()
	}

	public MergeResult(List<String> checkoutConflicts) {
		this.checkoutConflicts = checkoutConflicts;
		this.mergeStatus = MergeStatus.CHECKOUT_CONFLICT;
	}

	public ObjectId getNewHead() {
		return newHead;
	}

	public MergeStatus getMergeStatus() {
		return mergeStatus;
	}

	public ObjectId[] getMergedCommits() {
		return mergedCommits;
	}

	public ObjectId getBase() {
		return base;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		boolean first = true;
		StringBuilder commits = new StringBuilder();
		for (ObjectId commit : mergedCommits) {
			if (!first)
				commits.append("
			else
				first = false;
			commits.append(ObjectId.toString(commit));
		}
		return MessageFormat.format(
				JGitText.get().mergeUsingStrategyResultedInDescription
				commits
				mergeStatus
	}

	public void setConflicts(Map<String
		this.conflicts = conflicts;
	}

	public void addConflict(String path
		if (conflicts == null)
			conflicts = new HashMap<>();
		conflicts.put(path
	}

	public void addConflict(String path
		if (!lowLevelResult.containsConflicts())
			return;
		if (conflicts == null)
			conflicts = new HashMap<>();
		int nrOfConflicts = 0;
		for (MergeChunk mergeChunk : lowLevelResult) {
			if (mergeChunk.getConflictState().equals(ConflictState.FIRST_CONFLICTING_RANGE)) {
				nrOfConflicts++;
			}
		}
		int currentConflict = -1;
		int[][] ret=new int[nrOfConflicts][mergedCommits.length+1];
		for (MergeChunk mergeChunk : lowLevelResult) {
			int endOfChunk = 0;
			if (mergeChunk.getConflictState().equals(ConflictState.FIRST_CONFLICTING_RANGE)) {
				if (currentConflict > -1) {
					ret[currentConflict][mergedCommits.length] = endOfChunk;
				}
				currentConflict++;
				endOfChunk = mergeChunk.getEnd();
				ret[currentConflict][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
			}
			if (mergeChunk.getConflictState().equals(ConflictState.NEXT_CONFLICTING_RANGE)) {
				if (mergeChunk.getEnd() > endOfChunk)
					endOfChunk = mergeChunk.getEnd();
				ret[currentConflict][mergeChunk.getSequenceIndex()] = mergeChunk.getBegin();
			}
		}
		conflicts.put(path
	}

	public Map<String
		return conflicts;
	}

	public Map<String
		return failingPaths;
	}

	public List<String> getCheckoutConflicts() {
		return checkoutConflicts;
	}
}
