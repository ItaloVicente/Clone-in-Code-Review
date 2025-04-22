	private void readIndex() throws TeamException {
		try {
			newTree = commit.getTree();
			index = repository.getIndex();
		} catch (IOException e) {
			throw new TeamException(CoreText.ResetOperation_readingIndex, e);
		}
	}

