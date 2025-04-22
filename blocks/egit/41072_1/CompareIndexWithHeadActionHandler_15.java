
		if (resources.length > 0) {
			CompareUtils.compare(resources, repository, GitFileRevision.INDEX,
					Constants.HEAD, false, workBenchPage);
		} else {
			IPath[] locations = getSelectedLocations(event);
			if (locations.length > 0)
				CompareUtils.compare(locations[0], repository,
