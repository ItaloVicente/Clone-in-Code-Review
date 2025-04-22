				MergeResult result;
				if (gitLinkMerging) {
					result = createGitLinksMergeResult(base
				} else {
					result = contentMerge(base
				}
