	protected HashedSequence<Subsequence<S>> b;

	private MyersDiff(SequenceComparator<? super S> cmp
		Subsequence<S> as = Subsequence.a(a
		Subsequence<S> bs = Subsequence.b(b

		HashedSequencePair<Subsequence<S>> pair = new HashedSequencePair<Subsequence<S>>(
				new SubsequenceComparator<S>(cmp)

		this.cmp = pair.getComparator();
		this.a = pair.getA();
		this.b = pair.getB();
