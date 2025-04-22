			if (dirCacheIterator != null) {
				if (workingTreeIterator == null) {
					if (!isEntryGitLink(dirCacheIterator)
							|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
						missing.add(treeWalk.getPathString());
				} else {
					if (workingTreeIterator.isModified(
							dirCacheIterator.getDirCacheEntry(), true,
							treeWalk.getObjectReader())) {
						if (!isEntryGitLink(dirCacheIterator) || !isEntryGitLink(workingTreeIterator)
								|| (ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL && ignoreSubmoduleMode != IgnoreSubmoduleMode.DIRTY))
							modified.add(treeWalk.getPathString());
