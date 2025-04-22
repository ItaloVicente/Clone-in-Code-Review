		if (dstRev == GitFileRevision.INDEX) {
			final IResource[] resourcesArray = includedResources
					.toArray(new IResource[includedResources.size()]);
			openGitTreeCompare(resourcesArray, srcRev,
					CompareTreeView.INDEX_VERSION, includeLocal);
		} else if (srcRev == GitFileRevision.INDEX) {
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
		final Set<IResource> resources = new HashSet<IResource>();
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
