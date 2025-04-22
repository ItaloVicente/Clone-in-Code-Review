		FileDiffContentProvider contentProvider = (FileDiffContentProvider) getContentProvider();
		contentProvider.setInterestingPaths(interestingPaths);
		if (interestingPaths == null)
			((FileDiffLabelProvider) getLabelProvider())
					.setAllInteresting(true);
		else
			updateLabelProviderFlags();
