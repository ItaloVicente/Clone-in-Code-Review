	private Collection<IPath> getSelectedAndRelatedLocations(ExecutionEvent event)
			throws ExecutionException {
		final IPath[] selectedLocations = getSelectedLocations(event);
		if (selectedLocations.length == 0)
			return Collections.emptyList();

		final LinkedHashSet<IPath> locations = new LinkedHashSet<IPath>(
				Arrays.asList(selectedLocations));

		final IResource[] selectedResources = getSelectedResources(event);
		if (selectedResources.length != 0) {
			final IWorkbenchPart part = getPart(event);
			try {
				final IResource[] resourcesInScope = GitScopeUtil
						.getRelatedChanges(part, selectedResources);
				for (IResource resource : resourcesInScope) {
					IPath l = resource.getLocation();
					if (l != null)
						locations.add(l);
				}
			} catch (InterruptedException e) {
				return Collections.emptyList();
			}
		}
		return locations;
	}

