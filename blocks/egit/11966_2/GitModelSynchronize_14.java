		if (dstRev == GitFileRevision.INDEX) {
			final IResource[] resourcesArray = includedResources
					.toArray(new IResource[includedResources.size()]);
			openGitTreeCompare(resourcesArray, srcRev,
					CompareTreeView.INDEX_VERSION);
		} else if (srcRev == GitFileRevision.INDEX) {
			final ResourceMapping[] mappings = allMappings
					.toArray(new ResourceMapping[allMappings.size()]);
			final GitSynchronizeData data = new GitSynchronizeData(repository,
					srcRev, dstRev, true);
			launch(new GitSynchronizeDataSet(data), mappings);
		} else {
			final ResourceMapping[] mappings = allMappings
					.toArray(new ResourceMapping[allMappings.size()]);
			final GitSynchronizeData data = new GitSynchronizeData(repository,
					srcRev, dstRev, includeLocal);
			launch(new GitSynchronizeDataSet(data), mappings);
		}
	}
