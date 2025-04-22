	private ITypedElement getElementForCommit(final Repository repository,
			final String gitPath, final ObjectId commitId) throws IOException {
		RevWalk rw = new RevWalk(repository);
		RevCommit commit = rw.parseCommit(commitId);
		rw.release();

		return CompareUtils.getFileRevisionTypedElement(gitPath, commit, repository);
	}

