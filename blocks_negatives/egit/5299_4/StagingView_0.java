	private final RefsChangedListener myRefsChangedListener = new RefsChangedListener() {
		public void onRefsChanged(RefsChangedEvent event) {
			reload(event.getRepository());
		}
	};

	private final IndexChangedListener myIndexChangedListener = new IndexChangedListener() {
		public void onIndexChanged(IndexChangedEvent event) {
			reload(event.getRepository());
		}
	};

