
package org.eclipse.jgit.diff;

public final class HashedSequenceComparator<S extends Sequence> extends
		SequenceComparator<HashedSequence<S>> {
	private final SequenceComparator<? super S> cmp;

	HashedSequenceComparator(SequenceComparator<? super S> cmp) {
		this.cmp = cmp;
	}

	@Override
	public boolean equals(HashedSequence<S> a
			HashedSequence<S> b
		return a.hashes[ai] == b.hashes[bi]
				&& cmp.equals(a.base
	}

	@Override
	public int hash(HashedSequence<S> seq
		return seq.hashes[ptr];
	}
}
