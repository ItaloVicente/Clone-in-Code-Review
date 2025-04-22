
	@Override
	public void close() throws IOException {
		try {
			flush();
		} finally {
			super.close();
		}
	}
