					add(tw.getRawPath()
					MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
							base
					result.setContainsConflicts(true);
					mergeResults.put(tw.getPathString()
					unmergedPaths.add(tw.getPathString());
				} else {
					MergeResult<RawText> result = contentMerge(base
							theirs

					if (ignoreConflicts) {
						result.setContainsConflicts(false);
						updateIndex(base
					} else {
						add(tw.getRawPath()
								0);
						add(tw.getRawPath()
								0);
						DirCacheEntry e = add(tw.getRawPath()
								DirCacheEntry.STAGE_3

						if (modeO == 0) {
							if (isWorktreeDirty(work
								return false;
							}
							if (nonTree(modeT)) {
								if (e != null) {
									addToCheckout(tw.getPathString()
											attributes);
								}
