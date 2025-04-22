	private static class Block<V extends ObjectId> {
		private static final int BITS = 6;

		static final int SIZE = 1 << BITS;

		static final int CHAIN_LENGTH = 8;
