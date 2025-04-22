
package org.eclipse.jgit.merge;

public class MergeChunk {
	public enum ConflictState {
		NO_CONFLICT

		FIRST_CONFLICTING_RANGE

		NEXT_CONFLICTING_RANGE
	};

	private int sequenceIndex;

	private int begin;

	private int end;

	private ConflictState conflictState;

	protected MergeChunk(int sequenceIndex
			ConflictState conflictState) {
		super();
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
