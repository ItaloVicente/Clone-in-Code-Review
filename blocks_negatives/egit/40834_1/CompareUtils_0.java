			Repository repository, String gitPath, ITypedElement base,
			String refName) throws IOException {
		final ITypedElement destCommit;
		ITypedElement commonAncestor = null;

		if (GitFileRevision.INDEX.equals(refName))
			destCommit = getIndexTypedElement(repository, gitPath);
		else if (Constants.HEAD.equals(refName))
			destCommit = getHeadTypedElement(repository, gitPath);
		else {
			final ObjectId destCommitId = repository.resolve(refName);
			RevWalk rw = new RevWalk(repository);
			RevCommit commit = rw.parseCommit(destCommitId);
			rw.release();
			destCommit = getFileRevisionTypedElement(gitPath, commit,
					repository);

			if (base != null && commit != null) {
				final ObjectId headCommitId = repository
						.resolve(Constants.HEAD);
				commonAncestor = getFileRevisionTypedElementForCommonAncestor(
						gitPath, headCommitId, destCommitId, repository);
			}
		}
