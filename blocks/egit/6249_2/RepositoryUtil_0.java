
	public String getShortBranch(Repository repository) throws IOException {
		Ref head = repository.getRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			return CoreText.RepositoryUtil_noHead;

		if (head.isSymbolic())
			return repository.getBranch();

		String id = head.getObjectId().name();
		String ref = mapCommitToRef(repository, id, false);
		if (ref != null)
			return Repository.shortenRefName(ref) + ' ' + id.substring(0, 7);
		else
			return id.substring(0, 7);
	}
