	public static void compareHeadWithWorkspace(Repository repository,
			IFile file) {
		RevCommit headCommit;
		try {
			headCommit = new RevWalk(repository).parseCommit(repository
					.resolve(Constants.HEAD));
		} catch (IOException e1) {
			return;
		}
		String path = RepositoryMapping.getMapping(file).getRepoRelativePath(
				file);
		ITypedElement base = CompareUtils.getFileRevisionTypedElement(path,
				headCommit, repository);
		IFileRevision nextFile = new WorkspaceFileRevision(file);
		String encoding;
		try {
			encoding = file.getCharset();
		} catch (CoreException e1) {
			encoding = null;
		}
		ITypedElement next = new FileRevisionTypedElement(nextFile, encoding);
		GitCompareFileRevisionEditorInput input = new GitCompareFileRevisionEditorInput(
				next, base, null);
		CompareUI.openCompareDialog(input);
	}

	public static void compareHeadWithWorkingTree(Repository repository,
			String path) {
		RevCommit headCommit;
		try {
			headCommit = new RevWalk(repository).parseCommit(repository
					.resolve(Constants.HEAD));
		} catch (IOException e1) {
			return;
		}
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

