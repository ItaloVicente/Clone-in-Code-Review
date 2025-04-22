	@NonNull
	public IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull FileSystemItem file) {
		ResourceState result = new ResourceState();
		IPath path = file.getAbsolutePath();
		Repository repository = Activator.getDefault().getRepositoryCache()
				.getRepository(path);
		if (repository == null || repository.isBare()) {
			return result;
