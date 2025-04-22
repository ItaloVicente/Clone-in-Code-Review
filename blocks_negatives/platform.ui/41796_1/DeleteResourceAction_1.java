


	/**
	 * Return an array of the currently selected resources.
	 * 
	 * @return the selected resources
	 */
	private IResource[] getSelectedResourcesArray() {
		List selection = getSelectedResources();
		IResource[] resources = new IResource[selection.size()];
		selection.toArray(resources);
		return resources;
	}

