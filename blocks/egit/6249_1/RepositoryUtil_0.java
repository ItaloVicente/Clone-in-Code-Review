
	public String getShortBranch(Repository repository) throws IOException {
		Ref head = repository.getRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			return CoreText.RepositoryUtil_noHead;

		if (head.isSymbolic())
			return repository.getBranch();

		String fullBranch = repository.getFullBranch();
		String refString = mapCommitToRef(repository, fullBranch, false);
		if (refString != null)
			return Repository.shortenRefName(refString) + ' '
					+ fullBranch.substring(0, 7);
		else
			return fullBranch.substring(0, 7);
	}
