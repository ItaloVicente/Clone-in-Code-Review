
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter != Repository.class) {
			return null;
		}
		return adapter.cast(walk.getRepository());
	}
