	private static void addPerspectiveAndDescendants(List perspectiveIds,
			String id) {
		IPerspectiveRegistry registry = PlatformUI.getWorkbench()
				.getPerspectiveRegistry();
		IPerspectiveDescriptor[] perspectives = registry.getPerspectives();
		for (int i = 0; i < perspectives.length; i++) {
