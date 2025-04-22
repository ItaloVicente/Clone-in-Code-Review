	private class Reader extends ObjectReader {
		private final DfsReader ctx = db.newReader();

		@Override
		public ObjectReader newReader() {
			return db.newReader();
