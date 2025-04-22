	private void resetCherryPick() throws CoreException {
		try {
			repository.writeCherryPickHead(null);
			repository.writeMergeCommitMsg(null);
		} catch (IOException e) {
			throw new TeamException(CoreText.ResetOperation_resetCherryPickFailed, e);
		}
	}

