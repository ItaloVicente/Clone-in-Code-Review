	ByteBuffer zeroCopyByteBuffer(int n) {
		ByteBuffer b = ByteBuffer.wrap(block);
		b.limit(n);
		return b;
	}

