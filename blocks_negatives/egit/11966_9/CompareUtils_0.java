		final ObjectId destCommitId = repository.resolve(refName);
		RevWalk rw = new RevWalk(repository);
		RevCommit commit = rw.parseCommit(destCommitId);
		rw.release();
		final ITypedElement destCommit = getFileRevisionTypedElement(gitPath,
				commit, repository);

		final ITypedElement commonAncestor;
		if (base != null && commit != null) {
			final ObjectId headCommitId = repository.resolve(Constants.HEAD);
			commonAncestor = getFileRevisionTypedElementForCommonAncestor(
					gitPath, headCommitId, destCommitId, repository);
		} else {
			commonAncestor = null;
