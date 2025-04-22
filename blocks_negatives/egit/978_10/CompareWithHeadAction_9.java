	public void execute(IAction action) throws InvocationTargetException {
		final IResource resource = getSelectedResources()[0];
		final RepositoryMapping mapping = RepositoryMapping.getMapping(resource.getProject());
		final Repository repository = mapping.getRepository();
		final String gitPath = mapping.getRepoRelativePath(resource);


		final IFile baseFile = (IFile) resource;
		final ITypedElement base = SaveableCompareEditorInput.createFileElement(baseFile);

		ITypedElement next;
		try {
			Ref head = repository.getRef(Constants.HEAD);
			RevWalk rw = new RevWalk(repository);
			RevCommit commit = rw.parseCommit(head.getObjectId());

			next = CompareUtils.getFileRevisionTypedElement(gitPath, commit,
					repository);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}


		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				base, next, null);
		CompareUI.openCompareEditor(in);
	}

	@Override
	public boolean isEnabled() {
		final IResource[] selectedResources = getSelectedResources();
		if (selectedResources.length != 1)
			return false;

		final IResource resource = selectedResources[0];
		if (!(resource instanceof IFile)) {
			return false;
		}
		final RepositoryMapping mapping = RepositoryMapping.getMapping(resource.getProject());
		return mapping != null;
