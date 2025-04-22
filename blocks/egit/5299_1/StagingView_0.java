	private IndexDiffChangedListener myIndexDiffListener = new IndexDiffChangedListener() {
		public void indexDiffChanged(Repository repository,
				IndexDiffData indexDiffData) {
			reload(repository);
		}
	};
