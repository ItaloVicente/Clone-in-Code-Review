			String message) throws CoreException {
		this.author = author;
		this.committer = committer;
		this.message = message;
		if (filesToCommit != null && filesToCommit.length > 0)
			setRepository(filesToCommit[0]);
		if (filesToCommit != null)
			commitFileList = buildFileList(Arrays.asList(filesToCommit));
		if (notIndexed != null)
			this.notIndexed = buildFileList(notIndexed);
		if (notTracked != null)
			this.notTracked = buildFileList(notTracked);
	}

	public CommitOperation(Repository repository, Collection<String> filesToCommit, Collection<String> notIndexed,
			Collection<String> notTracked, String author, String committer,
			String message) throws CoreException {
		this.repo = repository;
