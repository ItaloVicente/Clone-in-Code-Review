	private ITypedElement getElementForRef(final Repository repository,
			final String gitPath, final String refName) throws IOException {
		RevWalk rw = new RevWalk(repository);
		RevCommit commit = rw.parseCommit(commitId);
		rw.release();

		return CompareUtils.getFileRevisionTypedElement(gitPath, commit, repository);
	}

