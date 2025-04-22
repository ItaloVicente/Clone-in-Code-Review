	/**
	 * Something that can supply an {@link InputStream}.
	 */
	private interface StreamSupplier {
		InputStream load() throws IOException;
	}

	/**
	 * We write the patch result to a {@link TemporaryBuffer} and then use
	 * {@link DirCacheCheckout}.getContent() to run the result through the CR-LF
	 * and smudge filters. DirCacheCheckout needs an ObjectLoader, not a
	 * TemporaryBuffer, so this class bridges between the two, making any Stream
	 * provided by a {@link StreamSupplier} look like an ordinary git blob to
	 * DirCacheCheckout.
	 */
	private static class StreamLoader extends ObjectLoader {

		private StreamSupplier data;

		private long size;

		StreamLoader(StreamSupplier data, long length) {
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
		public ObjectStream openStream()
				throws MissingObjectException, IOException {
			return new ObjectStream.Filter(getType(), getSize(),
					new BufferedInputStream(data.load()));
		}
	}

