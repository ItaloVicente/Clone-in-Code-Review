	public void addAll(List<? extends Reftable> readers) throws IOException {
		tables.addAll(readers);
		for (Reftable r : readers) {
			if (r instanceof ReftableReader) {
				adjustUpdateIndexes((ReftableReader) r);
			}
