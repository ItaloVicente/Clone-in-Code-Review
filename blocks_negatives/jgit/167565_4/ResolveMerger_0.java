				if (gitlinkConflict) {
					MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
							base, ours, theirs);
					result.setContainsConflicts(true);
					mergeResults.put(tw.getPathString(), result);
					if (!ignoreConflicts) {
						unmergedPaths.add(tw.getPathString());
					}
				} else {
