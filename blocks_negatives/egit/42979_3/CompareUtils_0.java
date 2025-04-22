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
