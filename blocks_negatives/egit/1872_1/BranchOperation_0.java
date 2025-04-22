	private void writeIndex() throws TeamException {
		try {
			index.write();
		} catch (IOException e) {
			throw new TeamException(CoreText.BranchOperation_writingIndex, e);
		}
	}

