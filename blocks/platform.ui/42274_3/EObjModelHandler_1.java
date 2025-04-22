	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adapterType.isInstance(this)) {
			@SuppressWarnings("unchecked")
			T modelHandlerBase = (T) this;
			return modelHandlerBase;
		}
		return null;
