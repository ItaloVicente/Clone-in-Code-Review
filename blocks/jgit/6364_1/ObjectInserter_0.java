	public static abstract class Filter extends ObjectInserter {
		protected abstract ObjectInserter delegate();

		@Override
		protected byte[] buffer() {
			return delegate().buffer();
		}

		public ObjectId idFor(int type
			return delegate().idFor(type
		}

		public ObjectId idFor(int type
			return delegate().idFor(type
		}

		public ObjectId idFor(int objectType
				throws IOException {
			return delegate().idFor(objectType
		}

		public ObjectId idFor(TreeFormatter formatter) {
			return delegate().idFor(formatter);
		}

		public ObjectId insert(int type
			return delegate().insert(type
		}

		public ObjectId insert(int type
				throws IOException {
			return delegate().insert(type
		}

		public ObjectId insert(int objectType
				throws IOException {
			return delegate().insert(objectType
		}

		public PackParser newPackParser(InputStream in) throws IOException {
			return delegate().newPackParser(in);
		}

		public void flush() throws IOException {
			delegate().flush();
		}

		public void release() {
			delegate().release();
		}
	}

