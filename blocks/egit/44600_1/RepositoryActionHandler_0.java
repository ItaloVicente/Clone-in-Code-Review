	private TreeFilter getFilterOverLogicalModel(IResource resource,
			Repository repository) throws IOException {

		DiffConfig diffConfig = repository.getConfig().get(DiffConfig.KEY);
		String path = RepositoryMapping.getMapping(resource.getProject())
				.getRepoRelativePath(resource);
		TreeFilter filter = FollowFilter.create(path, diffConfig);

		if (!Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {
			return filter;
		}
		ResourceMappingContext mappingContext = CompareUtils.prepareContext(
				repository, Constants.HEAD, Constants.HEAD, true);
		final ResourceMapping[] mappings = ResourceUtil.getResourceMappings(
				resource, mappingContext);
		for (ResourceMapping mapping : mappings) {
			try {
				final ResourceTraversal[] traversals = mapping.getTraversals(
						mappingContext, null);
				for (ResourceTraversal traversal : traversals) {
					final IResource[] resources = traversal.getResources();
					if (resources.length > 1
							&& Arrays.asList(resources).contains(resource)) {
						for (IResource r : resources) {
							if (r.equals(resource))
								continue;
							path = RepositoryMapping.getMapping(r.getProject())
									.getRepoRelativePath(r);
							filter = OrTreeFilter.create(filter,
									FollowFilter.create(path, diffConfig));
						}
					}
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return filter;
	}

