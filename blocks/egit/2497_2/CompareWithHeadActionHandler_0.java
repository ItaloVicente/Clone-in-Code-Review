		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;
		final IResource[] resources = getSelectedResources(event);

		if (resources.length == 1 && resources[0] instanceof IFile) {
			final IFile baseFile = (IFile) resources[0];
			final String gitPath = RepositoryMapping.getMapping(
					baseFile.getProject()).getRepoRelativePath(baseFile);
			final ITypedElement base = SaveableCompareEditorInput
					.createFileElement(baseFile);
