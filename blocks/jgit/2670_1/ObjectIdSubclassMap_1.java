	}

	private void init() {
		dirTable.add(newBlock(1));
		dirTable.add(newBlock(1));
		dirBits = 1;
		dirShift = 32 - dirBits;
	}

	private Block<V> newBlock(int newDepth) {
		if (freeList.isEmpty())
			return new Block<V>(newDepth);

		Block<V> b = freeList.remove(freeList.size() - 1);
		b.reset(newDepth);
		return b;
	}

	private int hash(AnyObjectId toFind) {
		return toFind.w1 >>> dirShift;
