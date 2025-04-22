	public <T> T getAdapter(Class<T> adapter) {
		if (Repository.class == adapter) {
			return adapter.cast(repository);
		}
		if (RevCommit.class == adapter) {
			return adapter.cast(commit);
		}
