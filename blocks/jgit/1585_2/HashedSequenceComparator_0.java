
package org.eclipse.jgit.diff;

public final class HashedSequence<S extends Sequence> extends Sequence {
	final S base;

	final int[] hashes;

	HashedSequence(S base
		this.base = base;
		this.hashes = hashes;
	}

	@Override
	public int size() {
		return base.size();
	}
}
