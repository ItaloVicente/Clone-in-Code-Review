		final Repository repository = update.repository;
		if (isWorkspace) {
			for (String file : indexDiff.getMissing())
				if (indexDiff.getChanged().contains(file))
					nodes.add(new StagingEntry(repository, MISSING_AND_CHANGED,
							file));
				else
					nodes.add(new StagingEntry(repository, MISSING, file));
			for (String file : indexDiff.getModified())
