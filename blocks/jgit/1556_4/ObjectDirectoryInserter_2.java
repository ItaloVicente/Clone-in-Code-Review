	void writeHeader(OutputStream out
			throws IOException {
		out.write(Constants.encodedTypeString(type));
		out.write((byte) ' ');
		out.write(Constants.encodeASCII(len));
		out.write((byte) 0);
	}

	File newTempFile() throws IOException {
		return File.createTempFile("noz"
	}

	DeflaterOutputStream compress(final OutputStream out) {
