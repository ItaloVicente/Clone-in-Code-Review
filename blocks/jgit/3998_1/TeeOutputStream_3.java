	@Override
	public void write(int val) throws IOException {
		dst1.write(val);
		dst2.write(val);
	}

	@Override
	public void flush() throws IOException {
		dst1.flush();
		dst2.flush();
	}

	@Override
	public void close() throws IOException {
		dst1.close();
		dst2.close();
	}
