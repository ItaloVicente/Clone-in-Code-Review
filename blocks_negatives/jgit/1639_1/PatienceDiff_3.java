	public <S extends Sequence, C extends SequenceComparator<? super S>> EditList diff(
			C cmp, S a, S b) {
		Edit region = new Edit(0, a.size(), 0, b.size());
		region = cmp.reduceCommonStartEnd(a, b, region);

		switch (region.getType()) {
		case INSERT:
		case DELETE: {
			EditList r = new EditList();
			r.add(region);
			return r;
		}

		case REPLACE: {
			SubsequenceComparator<S> cs = new SubsequenceComparator<S>(cmp);
			Subsequence<S> as = Subsequence.a(a, region);
			Subsequence<S> bs = Subsequence.b(b, region);
			return Subsequence.toBase(diffImpl(cs, as, bs), as, bs);
		}

		case EMPTY:
			return new EditList();

		default:
			throw new IllegalStateException();
		}
	}

	private <S extends Sequence, C extends SequenceComparator<? super S>> EditList diffImpl(
			C cmp, S a, S b) {
