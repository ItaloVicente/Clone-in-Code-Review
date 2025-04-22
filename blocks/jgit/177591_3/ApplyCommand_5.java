	private static class BufferLoader extends ObjectLoader {

		private TemporaryBuffer data;

		BufferLoader(TemporaryBuffer data) {
			this.data = data;
		}

		@Override
		public int getType() {
			return Constants.OBJ_BLOB;
		}

		@Override
		public long getSize() {
			return data.length();
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
					data.openInputStream());
		}
	}

	private void apply(Repository repository
			FileHeader fh
