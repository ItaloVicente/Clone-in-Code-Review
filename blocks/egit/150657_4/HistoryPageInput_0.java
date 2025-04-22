
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (Repository.class == adapter) {
			return adapter.cast(repo);
		}
		return null;
	}
