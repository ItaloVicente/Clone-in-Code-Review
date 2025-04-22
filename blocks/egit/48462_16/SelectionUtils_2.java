		Set<IResource> result = getSelectedResourcesSet(selection);
		return result.toArray(new IResource[result.size()]);
	}

	@NonNull
	private static Set<IResource> getSelectedResourcesSet(
			@NonNull IStructuredSelection selection) {
