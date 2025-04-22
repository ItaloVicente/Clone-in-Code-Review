
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return (T) (adapter.isAssignableFrom(walk.getRepository().getClass())
				? walk.getRepository() : null);
	}
