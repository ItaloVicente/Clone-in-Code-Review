	private void resetMerge() throws CoreException {
		try {
			repository.writeMergeHeads(null);
			repository.writeMergeCommitMsg(null);
		} catch (IOException e) {
			throw new TeamException(CoreText.ResetOperation_resetMergeFailed, e);
		}
	}

