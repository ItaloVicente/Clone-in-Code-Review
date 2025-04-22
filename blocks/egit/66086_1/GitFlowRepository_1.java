
	public boolean isOnDevelop(@NonNull RevCommit selectedCommit) throws IOException {
		String develop = config.getDevelopFull();
		return isOnBranch(selectedCommit, develop);
	}

	private boolean isOnBranch(RevCommit commit, String fullBranch)
			throws IOException {
		Ref developRef = repository.exactRef(fullBranch);
		try {
			List<Ref> list = Git.wrap(repository).branchList().setContains(commit.name()).call();

			return list.contains(developRef);
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}
