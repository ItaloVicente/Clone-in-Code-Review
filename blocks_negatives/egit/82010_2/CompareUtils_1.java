		boolean useTreeCompare = shouldUseTreeCompare(resources);
		if (!useTreeCompare) {
			if (includeLocal) {
				compareWorkspaceWithRef(repository, resources[0],
						rightRev, page);
			} else {
				compareBetween(repository, leftPath, rightPath, leftRev,
						rightRev, page);
			}
		} else {
			GitModelSynchronize.synchronize(resources, repository, leftRev,
					rightRev, includeLocal);
