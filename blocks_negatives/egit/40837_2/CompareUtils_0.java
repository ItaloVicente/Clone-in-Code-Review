	private static void compareWorkspaceWithRef(Repository repository,
			IFile file, String refName, IWorkbenchPage page) throws IOException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		final String gitPath = mapping.getRepoRelativePath(file);
		final ITypedElement base = SaveableCompareEditorInput
				.createFileElement(file);
