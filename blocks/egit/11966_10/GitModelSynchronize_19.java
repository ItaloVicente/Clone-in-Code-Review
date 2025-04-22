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
