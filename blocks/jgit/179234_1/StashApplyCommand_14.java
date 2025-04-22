					Merger ixMerger = strategy.newMerger(repo
					if (ixMerger instanceof ResolveMerger) {
						ResolveMerger resolver = (ResolveMerger) ixMerger;
						resolver.setCommitNames(new String[] { "stashed HEAD"
								"HEAD"
						resolver.setBase(stashHeadCommit);
						resolver.setContentMergeStrategy(contentStrategy);
					}
