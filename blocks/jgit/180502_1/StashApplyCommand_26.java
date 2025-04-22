					Merger ixMerger = strategy.newMerger(repo
					if (ixMerger instanceof ResolveMerger) {
						ResolveMerger resolveMerger = (ResolveMerger) ixMerger;
						resolveMerger.setCommitNames(new String[] { "stashed HEAD"
								"HEAD"
						resolveMerger.setBase(stashHeadCommit);
						resolveMerger.setContentMergeStrategy(contentStrategy);
					}
