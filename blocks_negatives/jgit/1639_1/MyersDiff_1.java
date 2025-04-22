	private MyersDiff(SequenceComparator<? super S> cmp, S a, S b, Edit region) {
		Subsequence<S> as = Subsequence.a(a, region);
		Subsequence<S> bs = Subsequence.b(b, region);

		HashedSequencePair<Subsequence<S>> pair = new HashedSequencePair<Subsequence<S>>(
				new SubsequenceComparator<S>(cmp), as, bs);
