	@Override
	protected boolean readIdentification(Buffer buffer) throws IOException {
		try {
			return super.readIdentification(buffer);
		} catch (IllegalStateException e) {
			throw new IOException(e.getLocalizedMessage(), e);
		}
	}

