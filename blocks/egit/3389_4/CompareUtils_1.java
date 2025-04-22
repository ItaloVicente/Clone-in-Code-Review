	public static void compareHeadWithWorkspace(Repository repository,
			IFile file) {
		RevCommit headCommit = getHeadCommit(repository);
		if (headCommit == null)
			return;
		String path = RepositoryMapping.getMapping(file).getRepoRelativePath(
				file);
		ITypedElement base = CompareUtils.getFileRevisionTypedElement(path,
				headCommit, repository);
		IFileRevision nextFile = new WorkspaceFileRevision(file);
		String encoding = null;
		try {
			encoding = file.getCharset();
		} catch (CoreException e) {
			Activator.handleError(UIText.CompareUtils_errorGettingEncoding, e, true);
		}
		ITypedElement next = new FileRevisionTypedElement(nextFile, encoding);
		GitCompareFileRevisionEditorInput input = new GitCompareFileRevisionEditorInput(
				next, base, null);
		CompareUI.openCompareDialog(input);
	}

	public static void compareHeadWithWorkingTree(Repository repository,
			String path) {
		RevCommit headCommit = getHeadCommit(repository);
		if (headCommit == null)
			return;
		ITypedElement base = CompareUtils.getFileRevisionTypedElement(path,
				headCommit, repository);
		IFileRevision nextFile;
		nextFile = new WorkingTreeFileRevision(new File(
				repository.getWorkTree(), path));
		String encoding = ResourcesPlugin.getEncoding();
		ITypedElement next = new FileRevisionTypedElement(nextFile, encoding);
		GitCompareFileRevisionEditorInput input = new GitCompareFileRevisionEditorInput(
				next, base, null);
		CompareUI.openCompareDialog(input);
	}

	private static RevCommit getHeadCommit(Repository repository) {
		RevCommit headCommit;
		try {
			ObjectId objectId = repository.resolve(Constants.HEAD);
			if (objectId == null) {
				Activator.handleError(
						UIText.CompareUtils_errorGettingHeadCommit, null, true);
				return null;
			}
			headCommit = new RevWalk(repository).parseCommit(objectId);
		} catch (IOException e) {
			Activator.handleError(UIText.CompareUtils_errorGettingHeadCommit,
					e, true);
			return null;
		}
		return headCommit;
	}

