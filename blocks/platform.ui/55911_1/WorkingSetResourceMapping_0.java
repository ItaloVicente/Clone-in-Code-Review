	public ResourceTraversal[] getTraversals(ResourceMappingContext context, IProgressMonitor mon)
			throws CoreException {
		ResourceMapping[] mappings = getMappings();
		SubMonitor subMonitor = SubMonitor.convert(mon, mappings.length);

		List<ResourceTraversal> result = new ArrayList<>();
		for (int i = 0; i < mappings.length; i++) {
			ResourceMapping mapping = mappings[i];
			result.addAll(Arrays.asList(mapping.getTraversals(context, subMonitor.newChild(1))));
