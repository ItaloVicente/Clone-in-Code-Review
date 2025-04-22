					Merger untrackedMerger = strategy.newMerger(repo
					if (untrackedMerger instanceof ResolveMerger) {
						ResolveMerger resolver = (ResolveMerger) untrackedMerger;
						resolver.setCommitNames(new String[] { "null"
						resolver.setBase(null);
						resolver.setContentMergeStrategy(contentStrategy);
					}
