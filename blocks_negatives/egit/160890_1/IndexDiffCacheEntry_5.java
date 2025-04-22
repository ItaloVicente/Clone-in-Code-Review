	private final IndexChangedListener indexChangedListener = new IndexChangedListener() {
		@Override
		public void onIndexChanged(IndexChangedEvent event) {
			refreshIndexDelta();
		}
	};
