			final IndexDiff indexDiff = update.indexDiff;
			final Repository repository = update.repository;
			if (isWorkspace) {
				for (String file : indexDiff.getMissing())
					nodes.add(new StagingEntry(repository, StagingEntry.State.MISSING, file));
				for (String file : indexDiff.getModified()) {
					if (indexDiff.getChanged().contains(file))
						nodes.add(new StagingEntry(repository, StagingEntry.State.PARTIALLY_MODIFIED, file));
					else
						nodes.add(new StagingEntry(repository, StagingEntry.State.MODIFIED, file));
				}
				for (String file : indexDiff.getUntracked())
					nodes.add(new StagingEntry(repository, StagingEntry.State.UNTRACKED, file));
				for (String file : indexDiff.getConflicting())
					nodes.add(new StagingEntry(repository, StagingEntry.State.CONFLICTING, file));
			} else {
				for (String file : indexDiff.getAdded())
					nodes.add(new StagingEntry(repository, StagingEntry.State.ADDED, file));
				for (String file : indexDiff.getChanged())
					nodes.add(new StagingEntry(repository, StagingEntry.State.CHANGED, file));
				for (String file : indexDiff.getRemoved())
					nodes.add(new StagingEntry(repository, StagingEntry.State.REMOVED, file));
