	RevFilter and(RevFilter a) {
		if (and == null) {
			and = a;
		} else {
			and = AndRevFilter.create(a
		}
		requiresCommitBody = and.requiresCommitBody();
		return this;
	}

	private MaxCountRevFilter(int maxCount
