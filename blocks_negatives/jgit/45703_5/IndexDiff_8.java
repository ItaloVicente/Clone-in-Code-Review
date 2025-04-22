					if (!isEntryGitLink(treeIterator)
							|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
						removed.add(treeWalk.getPathString());
					if (workingTreeIterator != null)
						untracked.add(treeWalk.getPathString());
				}
			} else {
				if (dirCacheIterator != null) {
					if (!isEntryGitLink(dirCacheIterator)
							|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
						added.add(treeWalk.getPathString());
				} else {
					if (workingTreeIterator != null
							&& !workingTreeIterator.isEntryIgnored()) {
						untracked.add(treeWalk.getPathString());
