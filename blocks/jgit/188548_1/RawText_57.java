	public static int getBufferSize() {
		return BUFFER_SIZE.get();
	}

	public static int setBufferSize(int bufferSize) {
		int newSize = Math.max(FIRST_FEW_BYTES
		return BUFFER_SIZE.updateAndGet(curr -> newSize);
