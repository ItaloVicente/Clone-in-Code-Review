
package org.eclipse.jgit.merge;

public class MergeChunk {
	public enum ConflictState {
		NO_CONFLICT

		FIRST_CONFLICTING_RANGE

		NEXT_CONFLICTING_RANGE
	};

	private final int sequenceIndex;

	private final int begin;

	private final int end;

	private final ConflictState conflictState;

	protected MergeChunk(int sequenceIndex
			ConflictState conflictState) {
		this.sequenceIndex = sequenceIndex;
		this.begin = begin;
		this.end = end;
		this.conflictState = conflictState;
	}

	public int getSequenceIndex() {
		return sequenceIndex;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public ConflictState getConflictState() {
		return conflictState;
	}
}
