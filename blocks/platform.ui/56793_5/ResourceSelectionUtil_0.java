	public static IStructuredSelection allResources(IStructuredSelection selection, int resourceMask) {
		Iterator<?> adaptables = selection.iterator();
		List<IResource> result = new ArrayList<>();
		while (adaptables.hasNext()) {
			Object next = adaptables.next();
			IResource resource = Adapters.getAdapter(next, IResource.class, true);
			if (resource == null) {
