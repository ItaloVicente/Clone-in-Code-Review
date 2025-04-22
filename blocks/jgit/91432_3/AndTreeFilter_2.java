			return matchFilter(walker) <= 0;
		}

		@Override
		public int matchFilter(TreeWalk walker)
				throws MissingObjectException
				IOException {
			final int ra = a.matchFilter(walker);
			if (ra == 1) {
				return 1;
			}
			final int rb = b.matchFilter(walker);
			if (rb == 1) {
				return 1;
			}
			if (ra == -1 || rb == -1) {
				return -1;
			}
			return 0;
