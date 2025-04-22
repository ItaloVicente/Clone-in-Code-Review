
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == GitInfo.class) {
			return Adapters.adapt(resource, adapter);
		}
		return null;
	}
