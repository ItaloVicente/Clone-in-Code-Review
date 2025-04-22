	public void writeReverseIndex(OutputStream stream) throws IOException {
		long writeStart = System.currentTimeMillis();
		final PackReverseIndexWriter writer = PackReverseIndexWriter.createWriter(stream);
		writer.write(getSortedByName()
		stats.timeWriting += System.currentTimeMillis() - writeStart;
	}

