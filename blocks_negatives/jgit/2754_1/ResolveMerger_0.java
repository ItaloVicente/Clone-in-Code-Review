			if (!inCore) {
				if (work != null
						&& (!nonTree(work.getEntryRawMode()) || work
								.isModified(index.getDirCacheEntry(), true))) {
					failingPaths.put(tw.getPathString(),
							MergeFailureReason.DIRTY_WORKTREE);
					return false;
				}
			}

