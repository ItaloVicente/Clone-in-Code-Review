
		TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
		if (w != null) {
			final IFileRevision fileRevision = GitFileRevision.inCommit(db,
					commit, gitPath, blobId);
			return fileRevision;
