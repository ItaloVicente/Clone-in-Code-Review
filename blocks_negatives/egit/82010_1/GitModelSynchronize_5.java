	public static final void synchronize(IResource[] resources,
			Repository repository, String srcRev, String dstRev,
			boolean includeLocal) throws IOException {
		final Set<IResource> includedResources = new HashSet<>(
				Arrays.asList(resources));
		final Set<ResourceMapping> allMappings = new HashSet<>();

		Set<IResource> newResources = new HashSet<>(
				includedResources);
		do {
			final Set<IResource> copy = newResources;
			newResources = new HashSet<>();
			for (IResource resource : copy) {
				ResourceMapping[] mappings = ResourceUtil.getResourceMappings(
						resource, ResourceMappingContext.LOCAL_CONTEXT);
				allMappings.addAll(Arrays.asList(mappings));
				newResources.addAll(collectResources(mappings));
			}
		} while (includedResources.addAll(newResources));

		if (dstRev.equals(GitFileRevision.INDEX)) {
			final IResource[] resourcesArray = includedResources
					.toArray(new IResource[includedResources.size()]);
			openGitTreeCompare(resourcesArray, srcRev,
					CompareTreeView.INDEX_VERSION, includeLocal);
		} else if (srcRev.equals(GitFileRevision.INDEX)) {
			final ResourceMapping[] mappings = allMappings
					.toArray(new ResourceMapping[allMappings.size()]);
			final GitSynchronizeData data = new GitSynchronizeData(repository,
					srcRev, dstRev, true, includedResources);
			launch(new GitSynchronizeDataSet(data), mappings);
		} else {
			final ResourceMapping[] mappings = allMappings
					.toArray(new ResourceMapping[allMappings.size()]);
			final GitSynchronizeData data = new GitSynchronizeData(repository,
					srcRev, dstRev, includeLocal, includedResources);
			launch(new GitSynchronizeDataSet(data), mappings);
		}
	}

	private static Set<IResource> collectResources(ResourceMapping[] mappings) {
		final Set<IResource> resources = new HashSet<>();
		ResourceMappingContext context = ResourceMappingContext.LOCAL_CONTEXT;
		for (ResourceMapping mapping : mappings) {
			try {
				ResourceTraversal[] traversals = mapping.getTraversals(context,
						new NullProgressMonitor());
				for (ResourceTraversal traversal : traversals)
					resources.addAll(Arrays.asList(traversal.getResources()));
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return resources;
	}

	private static void openGitTreeCompare(IResource[] resources,
			String srcRev, String dstRev, boolean includeLocal) {
