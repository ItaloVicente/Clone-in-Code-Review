			Merger merger = strategy.newMerger(repo);
			boolean mergeSucceeded;
			if (merger instanceof ResolveMerger) {
				ResolveMerger resolveMerger = (ResolveMerger) merger;
				resolveMerger
						.setCommitNames(new String[] { "stashed HEAD"
				resolveMerger.setBase(stashHeadCommit);
				resolveMerger
						.setWorkingTreeIterator(new FileTreeIterator(repo));
				resolveMerger.setContentMergeStrategy(contentStrategy);
				mergeSucceeded = resolveMerger.merge(headCommit
				List<String> modifiedByMerge = resolveMerger.getModifiedFiles();
				if (!modifiedByMerge.isEmpty()) {
					repo.fireEvent(new WorkingTreeModifiedEvent(modifiedByMerge
							null));
				}
			} else {
				mergeSucceeded = merger.merge(headCommit
