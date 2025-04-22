		}
	}

	private List<IResource> extractResourcesFromMapping() {
		ResourceTraversal[] traversals;
		try {
			traversals = mapping.getTraversals(null, null);
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
			return Collections.emptyList();
		}

		if (traversals.length == 0)
			return Collections.emptyList();

		List<IResource> result = new ArrayList<>();
		for (ResourceTraversal traversal : traversals) {
			IResource[] resources = traversal.getResources();
			result.addAll(Arrays.asList(resources));
