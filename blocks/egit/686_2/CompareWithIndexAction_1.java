	private ITypedElement getHeadTypedElement(final IFile baseFile)
			throws InvocationTargetException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(baseFile
				.getProject());
		final Repository repository = mapping.getRepository();
		String gitPath = mapping.getRepoRelativePath(baseFile);

		try {
			GitIndex index = repository.getIndex();
			if (index.getEntry(gitPath) == null) {
				return new GitCompareFileRevisionEditorInput.EmptyTypedElement(
						NLS.bind(UIText.CompareWithIndexAction_FileNotInIndex,
								baseFile.getName()));
			}
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}

		IFileRevision nextFile = GitFileRevision.inIndex(repository, gitPath);
