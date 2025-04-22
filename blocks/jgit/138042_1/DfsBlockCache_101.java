
	@FunctionalInterface
	interface RefLoader<T> {
		Ref<T> load() throws IOException;
	}

	@FunctionalInterface
	interface ReadableChannelSupplier {
		ReadableChannel get() throws IOException;
	}
