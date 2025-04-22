
	@Override
	public long nextUpdateIndex() throws IOException {
		long updateIndex = 0;
		for (Reftable r : tables) {
			if (r instanceof ReftableReader) {
				updateIndex = Math.max(updateIndex
						((ReftableReader) r).maxUpdateIndex());
			}
		}
		return updateIndex + 1;
	}

