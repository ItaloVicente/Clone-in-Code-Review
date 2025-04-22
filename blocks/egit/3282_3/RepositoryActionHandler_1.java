	private void extractResourcesFromMapping(Set<IResource> result, Object o) {
		ResourceMapping mapping = (ResourceMapping) getAdapter(o, ResourceMapping.class);
		if (mapping != null) {
			ResourceTraversal[] traversals;
			try {
				traversals = mapping.getTraversals(null, null);
				for (ResourceTraversal traversal : traversals) {
					IResource[] resources = traversal.getResources();
					result.addAll(Arrays.asList(resources));
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
	}

