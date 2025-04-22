
package org.eclipse.jgit.diff;

public final class Subsequence<S extends Sequence> extends Sequence {
	public static <S extends Sequence> Subsequence<S> a(S a
		return new Subsequence<>(a
	}

	public static <S extends Sequence> Subsequence<S> b(S b
		return new Subsequence<>(b
	}

	public static <S extends Sequence> void toBase(Edit e
			Subsequence<S> b) {
		e.beginA += a.begin;
		e.endA += a.begin;

		e.beginB += b.begin;
		e.endB += b.begin;
	}

	public static <S extends Sequence> EditList toBase(EditList edits
			Subsequence<S> a
		for (Edit e : edits)
			toBase(e
		return edits;
	}

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
