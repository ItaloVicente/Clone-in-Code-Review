	/**
	 * Helper interface like {@link Supplier}, but possibly raising an
	 * {@link IOException}.
	 *
	 * @param <T>
	 *            return type
	 */
	@FunctionalInterface
	private interface FtpOperation<T> {

		T call() throws IOException;

	}

