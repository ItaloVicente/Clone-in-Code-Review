	ByteBuffer zeroCopyByteBuffer(int n) {
		ByteBuffer b = ByteBuffer.wrap(block);
		b.position(n);
		return b;
	}

