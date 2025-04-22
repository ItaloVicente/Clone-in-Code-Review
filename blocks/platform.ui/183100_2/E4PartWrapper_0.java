
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		T result = Adapters.adapt(wrappedPart.getObject(), adapter);
		if (result == null) {
			result = super.getAdapter(adapter);
		}
		return result;
	}
