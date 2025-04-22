	@NonNull
	public IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull File file) {
		ResourceState result = new ResourceState();
		File absoluteFile = file.getAbsoluteFile();
		IPath path = new org.eclipse.core.runtime.Path(absoluteFile.getPath());
		Repository repository = Activator.getDefault().getRepositoryCache()
				.getRepository(path);
		if (repository == null || repository.isBare()) {
			return result;
		}
		File workTree = repository.getWorkTree();
		String repoRelativePath = path.makeRelativeTo(
				new org.eclipse.core.runtime.Path(workTree.getAbsolutePath()))
				.toString();
		if (repoRelativePath.isEmpty()
				|| repoRelativePath.equals(path.toString())) {
			return result;
