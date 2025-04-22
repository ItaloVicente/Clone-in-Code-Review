		if (mapping == null)
			return Collections.emptyList();

		ResourceTraversal[] traversals;
		try {
			traversals = mapping.getTraversals(null, null);
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
			return Collections.emptyList();
