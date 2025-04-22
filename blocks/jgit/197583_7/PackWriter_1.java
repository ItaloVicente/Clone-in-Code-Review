	public boolean isReverseIndexEnabled() throws IOException {
		return config.isWriteReverseIndex() && !isIndexDisabled();
	}

	public void writeReverseIndex(OutputStream stream) throws IOException {
		if (!isReverseIndexEnabled()) {
			return;
		}
		long writeStart = System.currentTimeMillis();
		final PackReverseIndexWriter writer = PackReverseIndexWriter.createWriter(stream);
		writer.write(getSortedByName()
		stats.timeWriting += System.currentTimeMillis() - writeStart;
	}

