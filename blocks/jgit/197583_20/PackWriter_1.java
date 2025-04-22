	public boolean isReverseIndexEnabled() {
		return config.isWriteReverseIndex() && !isIndexDisabled();
	}

	public void writeReverseIndex(OutputStream stream) throws IOException {
		if (!isReverseIndexEnabled()) {
			return;
		}
		long writeStart = System.currentTimeMillis();
		PackReverseIndexWriter writer = PackReverseIndexWriter
				.createWriter(stream);
		writer.write(sortByName()
		stats.timeWriting += System.currentTimeMillis() - writeStart;
	}

