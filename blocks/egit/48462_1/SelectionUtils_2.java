		Set<IResource> result = getSelectedResourcesSet(selection);
		return result.toArray(new IResource[result.size()]);
	}

	public static Set<IResource> getSelectedResourcesSet(
			IStructuredSelection selection) {
