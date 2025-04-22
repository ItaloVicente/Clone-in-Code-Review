	public static <S extends Sequence> Subsequence<S> a(S a
		return new Subsequence<S>(a
	}

	public static <S extends Sequence> Subsequence<S> b(S b
		return new Subsequence<S>(b
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

