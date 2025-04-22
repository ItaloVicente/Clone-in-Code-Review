
package org.eclipse.jgit.diff;

public class HashedSequencePair<S extends Sequence> {
	private final SequenceComparator<? super S> cmp;

	private final S baseA;

	private final S baseB;

	private final Edit region;

	private HashedSequence<S> cachedA;

	private HashedSequence<S> cachedB;

	public HashedSequencePair(SequenceComparator<? super S> cmp
			Edit region) {
		this.cmp = cmp;
		this.baseA = a;
		this.baseB = b;
		this.region = region;
	}

	public HashedSequenceComparator<S> getComparator() {
		return new HashedSequenceComparator<S>(cmp);
	}

	public HashedSequence<S> getA() {
		if (cachedA == null)
			cachedA = wrap(baseA
		return cachedA;
	}

	public HashedSequence<S> getB() {
		if (cachedB == null)
			cachedB = wrap(baseB
		return cachedB;
	}

	private HashedSequence<S> wrap(S base
		final int begin = ptr;
		int[] hashes = new int[end - ptr];
		int i = 0;
		while (ptr < end)
			hashes[i++] = cmp.hash(base
		return new HashedSequence<S>(base
	}
}
