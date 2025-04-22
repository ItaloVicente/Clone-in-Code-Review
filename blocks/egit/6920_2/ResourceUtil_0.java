	public static Collection<IResource> getMappedResources(Object element) {
		ResourceMapping mapping = AdapterUtils.adapt(element, ResourceMapping.class);
		if (mapping != null) {
			try {
				Collection<IResource> result = new ArrayList<IResource>();
				ResourceTraversal[] traversals = mapping.getTraversals(
						ResourceMappingContext.LOCAL_CONTEXT, null);
				for (ResourceTraversal traversal : traversals) {
					IResource[] resources = traversal.getResources();
					result.addAll(Arrays.asList(resources));
				}
				return result;
			} catch (CoreException e) {
				return Collections.emptyList();
			}
		}

		IResource resource = AdapterUtils.adapt(element, IResource.class);
		if (resource != null)
			return Arrays.asList(resource);

		return Collections.emptyList();
	}
