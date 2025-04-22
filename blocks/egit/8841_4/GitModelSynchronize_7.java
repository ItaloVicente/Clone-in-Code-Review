	public static final void synchronizeModelWithWorkspace(IFile file,
			Repository repository, String refName) throws IOException {
		synchronizeModel(file, new GitSynchronizeData(repository,
				Constants.HEAD, refName, true));
	}

	public static final void synchronizeModelBetweenRefs(IFile file,
			Repository repository, String sourceRef, String destinationRef) throws IOException {
		synchronizeModel(file, new GitSynchronizeData(repository,
				sourceRef, destinationRef, false));
	}

	private static void synchronizeModel(IFile file, final GitSynchronizeData data) {
		final GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		final ResourceMapping[] mappings = ResourceUtil.getResourceMappings(
				file, ResourceMappingContext.LOCAL_CONTEXT);

		GitModelSynchronize.launch(dataSet, mappings);
	}

