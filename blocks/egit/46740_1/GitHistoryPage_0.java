	private IResource[] getResourcesFromInput() {
		IResource[] items = input.getItems();
		if (items == null)
			return null;
		HashSet<IResource> result = new HashSet<IResource>();
		result.addAll(Arrays.asList(items));

		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {
			try {
				ResourceMappingContext context = CompareUtils.prepareContext(
						input.getRepository(), Constants.HEAD, Constants.HEAD,
						true);
				for (IResource item : items) {
					final ResourceMapping[] mappings = ResourceUtil
							.getResourceMappings(item, context);
					for (ResourceMapping mapping : mappings) {
						try {
							final ResourceTraversal[] traversals = mapping
									.getTraversals(context, null);
							for (ResourceTraversal traversal : traversals) {
								final List<IResource> traversalResources = Arrays
										.asList(traversal.getResources());
								if (traversalResources.size() > 1
										&& traversalResources.contains(item)) {
									result.addAll(traversalResources);
								}
							}
						} catch (CoreException e) {
							Activator.logError(e.getMessage(), e);
						}
					}
				}
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}
		}

		IResource[] resourceArray = new IResource[result.size()];
		resourceArray = result.toArray(resourceArray);

		return resourceArray;
	}

