	public <S extends Sequence> EditList diff(
			SequenceComparator<? super S> cmp
		Edit region = cmp.reduceCommonStartEnd(a

		switch (region.getType()) {
		case INSERT:
		case DELETE:
			return EditList.singleton(region);

		case REPLACE: {
			SubsequenceComparator<S> cs = new SubsequenceComparator<S>(cmp);
			Subsequence<S> as = Subsequence.a(a
			Subsequence<S> bs = Subsequence.b(b
			return Subsequence.toBase(diffNonCommon(cs
		}

		case EMPTY:
			return new EditList(0);

		default:
			throw new IllegalStateException();
		}
	}

	private static <S extends Sequence> Edit coverEdit(S a
		return new Edit(0
	}

	public abstract <S extends Sequence> EditList diffNonCommon(
			SequenceComparator<? super S> cmp
