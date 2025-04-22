	public static ITypedElement getIndexTypedElement(final IPath location)
			throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(location);
		if (mapping != null)
			return getIndexTypedElement(mapping.getRepository(),
					mapping.getRepoRelativePath(location));
		else
			return null;
	}

	public static ITypedElement getIndexTypedElement(final IPath location,
			int stage) {
		RepositoryMapping mapping = RepositoryMapping.getMapping(location);
		if (mapping == null)
			return null;
		Repository repository = mapping.getRepository();
		String repoRelativePath = mapping.getRepoRelativePath(location);
		IFileRevision revision = GitFileRevision.inIndex(repository,
				repoRelativePath, stage);
		String encoding = CompareCoreUtils.getResourceEncoding(repository, repoRelativePath);
		return new FileRevisionTypedElement(revision, encoding);
	}

