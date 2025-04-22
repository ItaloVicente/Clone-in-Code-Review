		Set<IResource> result = getSelectedResourcesSet(selection);
		return result.toArray(new IResource[result.size()]);
	}

	private static Set<IResource> getSelectedResourcesSet(
			IStructuredSelection selection) {
