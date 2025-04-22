	private IResourceVariant findFolderVariant(IResource resource,
			Repository repository) {
		File workDir = repository.getWorkDir();
		if (resource.getLocation() == null)
			return null;
		File resourceLocation = resource.getLocation().toFile();
		String resLocationAbsolutePath = resourceLocation.getAbsolutePath();
