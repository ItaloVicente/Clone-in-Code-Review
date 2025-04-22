
	/**
	 * Supplier that propagates IOException.
	 *
	 * @param <T>
	 *            the return type which is expected from {@link #get()}
	 */
	@FunctionalInterface
	public interface SupplierWithIOException<T> {
		/**
		 * @return result
		 * @throws IOException
		 */
		T get() throws IOException;
	}
