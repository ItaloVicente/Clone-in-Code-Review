		}
	}

	private void writeIndex() throws CoreException {
		try {
			index.write();
		} catch (IOException e) {
			throw new TeamException(CoreText.ResetOperation_writingIndex, e);
