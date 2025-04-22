			return matchFilter(walker) <= 0;
		}

		@Override
		public int matchFilter(TreeWalk walker)
				throws MissingObjectException
				IOException {
			final int ra = a.matchFilter(walker);
			if (ra == 0) {
				return 0;
			}
			final int rb = b.matchFilter(walker);
			if (rb == 0) {
				return 0;
			}
			if (ra == -1 || rb == -1) {
				return -1;
			}
			return 1;
