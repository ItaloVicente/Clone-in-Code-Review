		Ref<T> load(@Nullable T old) throws IOException;
	}

	@FunctionalInterface
	interface RefValueChecker<T> {
		boolean hasValue(@Nullable T val) throws IOException;
