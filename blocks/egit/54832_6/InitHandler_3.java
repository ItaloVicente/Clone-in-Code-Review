
	private void createInitialCommit(Repository repository)
			throws ExecutionException {
		CommitHelper commitHelper = new CommitHelper(repository);

		CommitOperation commitOperation;
		try {
			commitOperation = new CommitOperation(repository,
					commitHelper.getAuthor(), commitHelper.getCommitter(),
					UIText.InitHandler_initialCommit);
			commitOperation.execute(null);
		} catch (CoreException e) {
			throw new ExecutionException(e.getMessage());
		}
	}

	private List<Ref> getBranches(Repository repository) throws ExecutionException {
		List<Ref> branchList;
		try {
			branchList = Git.wrap(repository).branchList().call();
		} catch (GitAPIException e) {
			throw new ExecutionException(e.getMessage());
		}
		return branchList;
	}
