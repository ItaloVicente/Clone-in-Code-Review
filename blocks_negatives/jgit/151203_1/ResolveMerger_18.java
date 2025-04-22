					add(tw.getRawPath(), base, DirCacheEntry.STAGE_1, EPOCH, 0);
					add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2, EPOCH, 0);
					add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3, EPOCH,
							0);
					unmergedPaths.add(tw.getPathString());
					mergeResults.put(
							tw.getPathString(),
							new MergeResult<>(Collections
									.<RawText> emptyList()));
