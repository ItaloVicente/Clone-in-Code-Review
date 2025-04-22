			if (gitLinkMerging && ignoreConflicts) {
				add(tw.getRawPath()
				return true;
			} else if (gitLinkMerging) {
				add(tw.getRawPath()
				add(tw.getRawPath()
				add(tw.getRawPath()
				MergeResult<SubmoduleConflict> result = createGitLinksMergeResult(
						base
				result.setContainsConflicts(true);
				mergeResults.put(tw.getPathString()
				unmergedPaths.add(tw.getPathString());
				return true;
			} else if (!attributes.canBeContentMerged()) {
