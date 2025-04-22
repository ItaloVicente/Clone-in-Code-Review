	private static String getFileStatus(IFile file) throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		String path = mapping.getRepoRelativePath(file);
		Repository repo = mapping.getRepository();
		AdaptableFileTreeIterator fileTreeIterator =
			new AdaptableFileTreeIterator(repo.getWorkTree(),
					ResourcesPlugin.getWorkspace().getRoot());
		IndexDiff indexDiff = new IndexDiff(repo, Constants.HEAD, fileTreeIterator);
		Set<String> repositoryPaths = Collections.singleton(path);
		indexDiff.setFilter(PathFilterGroup.createFromStrings(repositoryPaths));
		indexDiff.diff();
		return getFileStatus(path, indexDiff);
	}

