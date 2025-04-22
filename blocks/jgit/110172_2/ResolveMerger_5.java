
				if (gitlinkConflict) {
					MergeResult<SubmoduleSequence> result = new MergeResult<>(
							Arrays.asList(
									new SubmoduleSequence(base == null ? null : base.getEntryObjectId())
									new SubmoduleSequence(ours == null ? null : ours.getEntryObjectId())
									new SubmoduleSequence(theirs == null ? null : theirs.getEntryObjectId())
							));
					result.setContainsConflicts(true);
					mergeResults.put(tw.getPathString()
					if (!ignoreConflicts) {
						unmergedPaths.add(tw.getPathString());
					}
				} else {
					unmergedPaths.add(tw.getPathString());
				}
