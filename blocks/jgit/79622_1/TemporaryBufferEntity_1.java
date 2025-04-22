
	@Override
	public void close() {
		if (buffer != null) {
			buffer.destroy();
		}
	}
