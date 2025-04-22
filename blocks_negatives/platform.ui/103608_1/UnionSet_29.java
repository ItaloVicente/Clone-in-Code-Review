	private IStalenessConsumer stalenessConsumer = new IStalenessConsumer() {
		@Override
		public void setStale(boolean stale) {
			boolean oldStale = UnionSet.this.stale;
			UnionSet.this.stale = stale;
			if (stale && !oldStale) {
				fireStale();
			}
