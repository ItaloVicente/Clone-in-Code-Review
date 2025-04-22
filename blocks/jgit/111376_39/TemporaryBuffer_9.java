	public InputStream openInputStreamWithAutoDestroy() throws IOException {
		return new BlockInputStream() {
			@Override
			public void close() throws IOException {
				super.close();
				destroy();
			}
		};
	}

