			boolean mergeSucceeded = merger.merge(headCommit
			List<String> modifiedByMerge = merger.getModifiedFiles();
			if (!modifiedByMerge.isEmpty()) {
				repo.fireEvent(
						new WorkingTreeModifiedEvent(modifiedByMerge
			}
			if (mergeSucceeded) {
