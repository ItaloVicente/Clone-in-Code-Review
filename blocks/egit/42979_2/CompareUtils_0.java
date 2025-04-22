			try (RevWalk rw = new RevWalk(repository)) {
				RevCommit commit = rw.parseCommit(destCommitId);
				destCommit = getFileRevisionTypedElement(gitPath, commit,
						repository);

				if (base != null && commit != null) {
					final ObjectId headCommitId = repository
							.resolve(Constants.HEAD);
					commonAncestor = getFileRevisionTypedElementForCommonAncestor(
							gitPath, headCommitId, destCommitId, repository);
				}
