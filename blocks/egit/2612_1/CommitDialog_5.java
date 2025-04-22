	private static Status getFileStatus(IFile file) throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		String path = mapping.getRepoRelativePath(file);
		Repository repo = mapping.getRepository();
		AdaptableFileTreeIterator fileTreeIterator = new AdaptableFileTreeIterator(
				repo, ResourcesPlugin.getWorkspace().getRoot());
		IndexDiff indexDiff = new IndexDiff(repo, Constants.HEAD, fileTreeIterator);
		Set<String> repositoryPaths = Collections.singleton(path);
		indexDiff.setFilter(PathFilterGroup.createFromStrings(repositoryPaths));
		indexDiff.diff(null, 0, 0, ""); //$NON-NLS-1$
		return getFileStatus(path, indexDiff);
	}
