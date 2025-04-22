			if (input instanceof File) {
				File file = (File) input;
				Repository repo = getRepository(event);
				RevCommit leftCommit;
				try {
					leftCommit = new RevWalk(repo).parseCommit(repo
							.resolve(Constants.HEAD));
				} catch (Exception e) {
					throw new ExecutionException(e.getMessage(), e);
				}
				final String gitPath = getRepoRelativePath(repo, file);
				ITypedElement left = CompareUtils.getFileRevisionTypedElement(
						gitPath, leftCommit, repo);
				ITypedElement right = CompareUtils.getFileRevisionTypedElement(
						gitPath, commit, repo);
				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						left, right, null);
				openInCompare(event, in);
				return null;
			}
