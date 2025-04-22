		for (int i = 0; i < trees.length; i++) {
			final AbstractTreeIterator t = trees[i];
			if (t.matches == ch) {
				t.skip();
				t.matches = null;
			}
		}
