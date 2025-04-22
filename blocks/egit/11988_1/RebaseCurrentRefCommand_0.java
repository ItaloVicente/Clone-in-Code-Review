	private String getFullBranch(Repository repository)
			throws ExecutionException {
		try {
			return repository.getFullBranch();
		} catch (IOException e) {
			throw new ExecutionException(
					UIText.RebaseCurrentRefCommand_ErrorGettingCurrentBranchMessage,
					e);
		}
	}
