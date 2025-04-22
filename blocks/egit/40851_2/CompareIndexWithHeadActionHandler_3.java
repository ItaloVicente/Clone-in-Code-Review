	private Object getSingleSelectedObject(ExecutionEvent event)
			throws ExecutionException {
		IResource[] resources = getSelectedResources();
		if (resources.length == 1) {
			return resources[0];
		} else {
			IPath[] locations = getSelectedLocations(event);
			if (locations.length == 1) {
				return locations[0];
			}
		}
