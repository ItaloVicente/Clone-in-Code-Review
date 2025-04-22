	private static ResourceMapping[] getSelectedResourceMappings(IResource[] elements) {
		ArrayList providerMappings = new ArrayList();
		for (int i = 0; i < elements.length; i++) {
			Object object = elements[i];
			Object adapted = getResourceMapping(object);
			if (adapted instanceof ResourceMapping) {
