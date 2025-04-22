		IWorkbenchPage workBenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		if (input instanceof IFile) {
			IFile file = (IFile) input;
			final RepositoryMapping mapping = RepositoryMapping.getMapping(file
					.getProject());
			final String gitPath = mapping.getRepoRelativePath(file);
			final String commitPath = getRenamedPath(gitPath, commit);
			ITypedElement right = CompareUtils.getFileRevisionTypedElement(
					commitPath, commit, mapping.getRepository());
			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					SaveableCompareEditorInput.createFileElement(file), right,
					null);
			CompareUtils.openInCompare(workBenchPage, in);
		} else if (input instanceof File) {
			File file = (File) input;
			Repository repo = getRepository(event);
			RevCommit leftCommit;
			try {
				leftCommit = new RevWalk(repo).parseCommit(repo
						.resolve(Constants.HEAD));
			} catch (Exception e) {
				throw new ExecutionException(e.getMessage(), e);
