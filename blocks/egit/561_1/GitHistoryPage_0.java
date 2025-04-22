	private IFileRevision getFileRevision(final IFile resource,
			final String gitPath, SWTCommit commit) throws IOException {

		TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
		if (w != null) {
			final IFileRevision fileRevision = GitFileRevision.inCommit(db,
					commit, gitPath, null);
			return fileRevision;
		}
		return null;
	}

