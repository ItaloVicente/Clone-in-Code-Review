		ResourceMapping[] mappings = getGitResourceMappings(resources);

		launch(gsdSet, mappings);
	}

	public static final void launch(final GitSynchronizeDataSet gsdSet,
			ResourceMapping[] mappings) {
