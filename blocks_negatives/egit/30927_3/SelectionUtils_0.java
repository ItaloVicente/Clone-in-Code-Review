		if (mapping != null) {
			ResourceTraversal[] traversals;
			try {
				traversals = mapping.getTraversals(null, null);
				for (ResourceTraversal traversal : traversals) {
					IResource[] resources = traversal.getResources();
					return Arrays.asList(resources);
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
