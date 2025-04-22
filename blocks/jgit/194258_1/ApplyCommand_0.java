	private interface StreamSupplier {
		InputStream load() throws IOException;
	}

	private static class StreamLoader extends ObjectLoader {

		private StreamSupplier data;

		private long size;

		StreamLoader(StreamSupplier data
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
				throws MissingObjectException
			return new ObjectStream.Filter(getType()
					new BufferedInputStream(data.load()));
		}
	}

