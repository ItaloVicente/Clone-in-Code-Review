
	@SuppressWarnings("unchecked")
	private static <T> T getAdapterFromManager(Object adaptable,
			Class<T> adapterClass) {
		Object adapter = Platform.getAdapterManager().getAdapter(adaptable,
				IResource.class);
		return (T) adapter;
	}

