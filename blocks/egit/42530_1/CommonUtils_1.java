	@SuppressWarnings("unchecked")
	public static <T> T getAdapter(IAdaptable adaptable,
			Class<T> adapterClass) {
		Object adapter = adaptable.getAdapter(adapterClass);
		return (T) adapter;
	}

