	/** {@inheritDoc} */
	@Override
	public void close() throws IOException {
		for (Reftable t : tables) {
			t.close();
		}
	}

