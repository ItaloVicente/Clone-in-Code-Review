			final String gitPath = getRepoRelativePath(repo, file);
			final String commitPath = getRenamedPath(gitPath, commit);
			ITypedElement left = CompareUtils.getFileRevisionTypedElement(
					gitPath, leftCommit, repo);
			ITypedElement right = CompareUtils.getFileRevisionTypedElement(
					commitPath, commit, repo);
			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					left, right, null);
			CompareUtils.openInCompare(workBenchPage, in);
			return null;
