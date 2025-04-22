			ResolveMerger merger = (ResolveMerger) strategy.newMerger(repo);
			merger.setCommitNames(new String[] { "stashed HEAD", "HEAD", //$NON-NLS-1$ //$NON-NLS-2$
			merger.setBase(stashHeadCommit);
			merger.setWorkingTreeIterator(new FileTreeIterator(repo));
			boolean mergeSucceeded = merger.merge(headCommit, stashCommit);
			List<String> modifiedByMerge = merger.getModifiedFiles();
			if (!modifiedByMerge.isEmpty()) {
				repo.fireEvent(
						new WorkingTreeModifiedEvent(modifiedByMerge, null));
