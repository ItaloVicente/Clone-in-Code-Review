	private MyersDiff(EditList edits
			HashedSequence<S> a
		this.edits = edits;
		this.cmp = cmp;
		this.a = a;
		this.b = b;
		calculateEdits(region);
