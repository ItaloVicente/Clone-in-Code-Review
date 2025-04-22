
package org.eclipse.jgit.merge;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.diff.Sequence;
import org.eclipse.jgit.merge.MergeChunk.ConflictState;
import org.eclipse.jgit.util.IntList;

public class MergeResult implements Iterable<MergeChunk> {
	private final List<Sequence> sequences;

	private final IntList chunks = new IntList();

	private boolean containsConflicts = false;

	public MergeResult(List<Sequence> sequences) {
		this.sequences = sequences;
	}

	public void add(int srcIdx
		chunks.add(conflictState.ordinal());
		chunks.add(srcIdx);
		chunks.add(begin);
		chunks.add(end);
		if (conflictState != ConflictState.NO_CONFLICT)
			containsConflicts = true;
	}

	public List<Sequence> getSequences() {
		return sequences;
	}

	private static final ConflictState[] states = ConflictState.values();

	public Iterator<MergeChunk> iterator() {
		return new Iterator<MergeChunk>() {
			int idx;

			public boolean hasNext() {
				return (idx < chunks.size());
			}

			public MergeChunk next() {
				ConflictState state = states[chunks.get(idx++)];
				int srcIdx = chunks.get(idx++);
				int begin = chunks.get(idx++);
				int end = chunks.get(idx++);
				return new MergeChunk(srcIdx
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public boolean containsConflicts() {
		return containsConflicts;
	}
}
