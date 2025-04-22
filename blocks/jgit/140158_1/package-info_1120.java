
package org.eclipse.jgit.diff;

public final class SubsequenceComparator<S extends Sequence> extends
		SequenceComparator<Subsequence<S>> {
	private final SequenceComparator<? super S> cmp;

	public SubsequenceComparator(SequenceComparator<? super S> cmp) {
		this.cmp = cmp;
	}

	@Override
	public boolean equals(Subsequence<S> a
		return cmp.equals(a.base
	}

	@Override
	public int hash(Subsequence<S> seq
		return cmp.hash(seq.base
	}
}
