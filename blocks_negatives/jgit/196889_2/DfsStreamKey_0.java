
	static final class ForReverseIndex extends DfsStreamKey {
		private final DfsStreamKey idxKey;

		ForReverseIndex(DfsStreamKey idxKey) {
			super(idxKey.hash + 1, null);
			this.idxKey = idxKey;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof ForReverseIndex
					&& idxKey.equals(((ForReverseIndex) o).idxKey);
		}
	}
