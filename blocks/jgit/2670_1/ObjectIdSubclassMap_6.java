		final V[] members;

		int depth;

		Block(int blockDepth) {
			members = Block.<V> createArray(SIZE);
			depth = blockDepth;
