					Merger untrackedMerger = strategy.newMerger(repo
					if (untrackedMerger instanceof ResolveMerger) {
						ResolveMerger resolveMerger = (ResolveMerger) untrackedMerger;
						resolveMerger.setCommitNames(new String[] { "null"
						resolveMerger.setBase(null);
						resolveMerger.setContentMergeStrategy(contentStrategy);
					}
