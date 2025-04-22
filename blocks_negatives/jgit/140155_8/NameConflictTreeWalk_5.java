		for (int i = 0; i < trees.length; i++) {
			final AbstractTreeIterator t = trees[i];
			if (t.matches == ch) {
				if (t.matchShift == 0)
					t.skip();
				else {
					t.back(t.matchShift);
					t.matchShift = 0;
				}
				t.matches = null;
			}
		}
