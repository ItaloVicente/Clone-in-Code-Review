
package org.eclipse.jgit.diff;

public final class Subsequence<S extends Sequence> extends Sequence {
	final S base;

	final int begin;

	private final int size;

	public Subsequence(S base
		this.base = base;
		this.begin = begin;
		this.size = end - begin;
	}

	@Override
	public int size() {
		return size;
	}
}
