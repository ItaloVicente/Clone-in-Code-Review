	private static ResourceMapping[] getSelectedResourceMappings(
			IResource[] elements) {
		List<ResourceMapping> providerMappings = new ArrayList<ResourceMapping>();

		for (IResource element : elements) {
			Object adapted = getResourceMapping(element);
			if (adapted != null && adapted instanceof ResourceMapping) {
