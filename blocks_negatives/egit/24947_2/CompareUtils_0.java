		final ResourceMapping[] mappings = ResourceUtil.getResourceMappings(
				file, ResourceMappingContext.LOCAL_CONTEXT);

		for (ResourceMapping mapping : mappings) {
			try {
				final ResourceTraversal[] traversals = mapping.getTraversals(
						ResourceMappingContext.LOCAL_CONTEXT, null);
				for (ResourceTraversal traversal : traversals) {
					final IResource[] resources = traversal.getResources();
					for (IResource resource : resources) {
						if (!resource.equals(file))
							return false;
