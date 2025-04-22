	public static boolean allResourcesAreOfType(IStructuredSelection selection,
			int resourceMask) {
		Iterator resources = selection.iterator();
		while (resources.hasNext()) {
			Object next = resources.next();
			if (!(next instanceof IResource)) {
