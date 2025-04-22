	/**
	 * Searches the workspace for markers that pass this filter.
	 *
	 * @return Collection of markers.
	 */
	Collection findMarkers(IProgressMonitor mon, boolean ignoreExceptions)
			throws CoreException {

		List unfiltered = Collections.EMPTY_LIST;

		if (!isEnabled()) {
			unfiltered = findMarkers(new IResource[] { ResourcesPlugin
					.getWorkspace().getRoot() }, IResource.DEPTH_INFINITE, -1,
					mon, ignoreExceptions);
		} else {
			int limit = -1;

			switch (getOnResource()) {
			case ON_ANY: {
				unfiltered = findMarkers(new IResource[] { ResourcesPlugin
						.getWorkspace().getRoot() }, IResource.DEPTH_INFINITE,
						limit, mon, ignoreExceptions);
				break;
			}
			case ON_SELECTED_ONLY: {
				unfiltered = findMarkers(focusResource, IResource.DEPTH_ZERO,
						limit, mon, ignoreExceptions);
				break;
			}
			case ON_SELECTED_AND_CHILDREN: {
				unfiltered = findMarkers(focusResource,
						IResource.DEPTH_INFINITE, limit, mon, ignoreExceptions);
				break;
			}
			case ON_ANY_IN_SAME_CONTAINER: {
				unfiltered = findMarkers(getProjects(focusResource),
						IResource.DEPTH_INFINITE, limit, mon, ignoreExceptions);
				break;
			}
			case ON_WORKING_SET: {
				unfiltered = findMarkers(getResourcesInWorkingSet(),
						IResource.DEPTH_INFINITE, limit, mon, ignoreExceptions);
			}
			}
		}

		if (unfiltered == null) {
			unfiltered = Collections.EMPTY_LIST;
		}

		return unfiltered;
	}

