						boolean isGitLink = isEntryGitLink(dirCacheIterator);
						if (!isGitLink
								|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL) {
							String path = treeWalk.getPathString();
							missing.add(path);
							if (isGitLink) {
								missingSubmodules.add(path);
							}
						}
