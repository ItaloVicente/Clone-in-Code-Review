	@Nullable
	public static HistoryPageInput getMostFittingInput(
			@NonNull IStructuredSelection selection, Object mandatoryObject) {
		Set<IResource> resources = getSelectedResourcesSet(selection);
		if (!resources.contains(mandatoryObject)) {
			return null;
		}

		Repository repository = ResourceUtil
				.getRepository((IResource) mandatoryObject);
		if (repository == null) {
			return null;
		}

		for (Iterator<IResource> it = resources.iterator(); it.hasNext();) {
			IResource resource = it.next();
			if (ResourceUtil.getRepository(resource) != repository) {
				it.remove();
			}
		}

		IResource[] resourceArray = resources.toArray(new IResource[resources
				.size()]);
		return new HistoryPageInput(repository, resourceArray);
	}

