	/**
	 * Something that can supply an {@link InputStream}.
	 */
	public interface StreamSupplier {

		/**
		 * Loads the input stream.
		 *
		 * @return the loaded stream
		 * @throws IOException
		 *             if any reading error occurs
		 */
		InputStream load() throws IOException;
	}

	/**
	 * We want to use DirCacheCheckout for its CR-LF and smudge filters, but DirCacheCheckout needs an
	 * ObjectLoader rather than InputStream. This class provides a bridge between the two.
	 */
	public static class StreamLoader extends ObjectLoader {

		private final StreamSupplier data;

		private final long size;

		private StreamLoader(StreamSupplier data, long length) {
			this.data = data;
			this.size = length;
		}

		@Override
		public int getType() {
			return Constants.OBJ_BLOB;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public boolean isLarge() {
			return true;
		}

		@Override
		public byte[] getCachedBytes() throws LargeObjectException {
			throw new LargeObjectException();
		}

		@Override
		public ObjectStream openStream() throws IOException {
			return new ObjectStream.Filter(getType(), getSize(),
					new BufferedInputStream(data.load()));
		}
	}

	/**
	 * Creates stream loader for the given supplier.
	 *
	 * @param supplier
	 *            to wrap
	 * @param length
	 *            of the supplied content
	 * @return the result stream loader
	 */
	public static StreamLoader createStreamLoader(StreamSupplier supplier,
			long length) {
		return new StreamLoader(supplier, length);
	}

