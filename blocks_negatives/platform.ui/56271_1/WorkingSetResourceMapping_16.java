	public ResourceTraversal[] getTraversals(ResourceMappingContext context, IProgressMonitor monitor) throws CoreException {
		if (monitor == null)
			monitor = new NullProgressMonitor();
		try {
			ResourceMapping[] mappings = getMappings();
			monitor.beginTask("", 100 * mappings.length); //$NON-NLS-1$
			List result = new ArrayList();
			for (int i = 0; i < mappings.length; i++) {
				ResourceMapping mapping = mappings[i];
				result.addAll(Arrays.asList(mapping.getTraversals(context, new SubProgressMonitor(monitor, 100))));
			}
			return (ResourceTraversal[]) result.toArray(new ResourceTraversal[result.size()]);
		} finally {
			monitor.done();
