	@SuppressWarnings("unchecked")
	public static <T> T getService(IServiceLocator locator, Class<T> api) {
		Object service = locator.getService(api);
		return (T) service;
	}

