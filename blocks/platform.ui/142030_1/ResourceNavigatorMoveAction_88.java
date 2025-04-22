		super.run();
		List destinations = getDestinations();
		if (destinations != null && destinations.isEmpty() == false) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			List resources = new ArrayList();
			Iterator iterator = destinations.iterator();
