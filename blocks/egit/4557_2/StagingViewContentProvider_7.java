		final IndexDiff indexDiff = update.indexDiff;
		final Repository repository = update.repository;
		if (isWorkspace) {
			for (String file : indexDiff.getMissing())
				nodes.add(new StagingEntry(repository, MISSING, file));
			for (String file : indexDiff.getModified()) {
				if (indexDiff.getChanged().contains(file))
					nodes.add(new StagingEntry(repository, PARTIALLY_MODIFIED,
							file));
				else
					nodes.add(new StagingEntry(repository, MODIFIED, file));
