		Ref<T> load(@Nullable T old) throws IOException;
	}

	@FunctionalInterface
	interface RefValueChecker<T> {
		boolean shouldReload(@Nullable T val) throws IOException;
