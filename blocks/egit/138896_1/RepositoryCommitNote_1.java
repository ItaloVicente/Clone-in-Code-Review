	public <T> T getAdapter(Class<T> adapter) {
		if (RepositoryCommit.class == adapter) {
			return adapter.cast(commit);
		}
		if (Repository.class == adapter) {
			return adapter.cast(commit.getRepository());
		}
