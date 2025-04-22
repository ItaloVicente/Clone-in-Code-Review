		IWorkbenchPage workBenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		final IResource[] resources = getSelectedResources(event);
		try {
			if (resources.length > 0)
				CompareUtils.compare(resources, repository, Constants.HEAD,
						GitFileRevision.INDEX, true, workBenchPage);
			else {
				IPath[] locations = getSelectedLocations(event);
				if (locations.length > 0)
					CompareUtils.compare(locations[0], repository,
							Constants.HEAD, GitFileRevision.INDEX, true,
							workBenchPage);
