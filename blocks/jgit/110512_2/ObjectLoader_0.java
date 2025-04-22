
	public static abstract class Filter extends ObjectLoader {
		protected abstract ObjectLoader delegate();

		@Override
		public int getType() {
			return delegate().getType();
		}

		@Override
		public long getSize() {
			return delegate().getSize();
		}

		@Override
		public boolean isLarge() {
			return delegate().isLarge();
		}

		@Override
		public byte[] getCachedBytes() {
			return delegate().getCachedBytes();
		}

		@Override
		public ObjectStream openStream() throws IOException {
			return delegate().openStream();
		}
	}
