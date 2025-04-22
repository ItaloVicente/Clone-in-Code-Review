	private int readFromInput() throws IOException {
		if (bAvail == 0)
			fillFromInput(1);
		bAvail--;
		final int b = buf[bOffset++] & 0xff;
		crc.update(b);
		return b;
	}

	private int readFromFile() throws IOException {
