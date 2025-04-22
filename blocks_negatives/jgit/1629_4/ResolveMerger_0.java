			if (work != null
					&& (!nonTree(work.getEntryRawMode()) || work.isModified(
							index.getDirCacheEntry(), true, true, db.getFS()))) {
				failingPathes.put(tw.getPathString(),
						MergeFailureReason.DIRTY_WORKTREE);
				return false;
