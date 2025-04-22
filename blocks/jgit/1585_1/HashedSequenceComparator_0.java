
package org.eclipse.jgit.diff;

public final class HashedSequence<S extends Sequence> extends Sequence {
	final S base;

	final int[] hashes;

	final int begin;

	HashedSequence(S base
		this.base = base;
		this.hashes = hashes;
		this.begin = begin;
	}

	@Override
	public int size() {
		return base.size();
	}
}
