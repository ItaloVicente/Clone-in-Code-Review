		this.in = in;
		this.detectBinary = detectBinary;
		this.abortIfBinary = abortIfBinary;
	}

	@Override
	public int read() throws IOException {
		final int read = read(single, 0, 1);
		return read == 1 ? single[0] & 0xff : -1;
