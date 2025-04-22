		for (int i = 0; i < trees.length; i++) {
			final AbstractTreeIterator t = trees[i];
			if (t.matches == ch) {
				t.next(1);
				t.matches = null;
			}
		}
