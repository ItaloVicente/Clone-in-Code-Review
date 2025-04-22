	public <T> T getAdapter(Class<T> adapter) {
		if (RepositoryCommit.class == adapter) {
			return adapter.cast(commit);
		}
		if (RevCommit.class == adapter) {
			return adapter.cast(commit.getRevCommit());
		}
		if (Repository.class == adapter) {
			return adapter.cast(commit.getRepository());
		}
