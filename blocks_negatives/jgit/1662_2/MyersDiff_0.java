	private MyersDiff(SequenceComparator<? super S> cmp, S a, S b) {
		HashedSequencePair<S> pair;

		pair = new HashedSequencePair<S>(cmp, a, b);
		this.cmp = pair.getComparator();
		this.a = pair.getA();
		this.b = pair.getB();

		calculateEdits();
