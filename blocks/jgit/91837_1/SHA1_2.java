	public void update(byte b) {
		int bufferLen = (int) (length & 63);
		length++;
		buffer[bufferLen] = b;
		if (bufferLen == 63) {
			compress(buffer
		}
	}

