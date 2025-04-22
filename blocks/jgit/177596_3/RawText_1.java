	public ByteBuffer getRawString(int i) {
		int s = getStart(i);
		int e = getEnd(i);
		if (e > 0 && content[e - 1] == '\n') {
			e--;
		}
		return ByteBuffer.wrap(content
	}

