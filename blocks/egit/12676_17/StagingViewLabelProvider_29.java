		if (element instanceof StagingFolderEntry) {
			StagingFolderEntry stagingFolderEntry = (StagingFolderEntry) element;
			return stagingFolderEntry.getNodePath().toString();
		}

		StagingEntry stagingEntry = (StagingEntry) element;
		final DecorationResult decoration = new DecorationResult();
		decorationHelper.decorate(decoration, stagingEntry);
