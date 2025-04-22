						if (!isEntryGitLink(dirCacheIterator) || !isEntryGitLink(workingTreeIterator)
								|| (ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL && ignoreSubmoduleMode != IgnoreSubmoduleMode.DIRTY))
							modified.add(treeWalk.getPathString());
					}
				}
			}
		}

		if (ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL) {
			IgnoreSubmoduleMode localIgnoreSubmoduleMode = ignoreSubmoduleMode;
			SubmoduleWalk smw = SubmoduleWalk.forIndex(repository);
			while (smw.next()) {
				try {
					if (localIgnoreSubmoduleMode == null)
						localIgnoreSubmoduleMode = smw.getModulesIgnore();
					if (IgnoreSubmoduleMode.ALL
							.equals(localIgnoreSubmoduleMode))
						continue;
				} catch (ConfigInvalidException e) {
					throw new IOException(
							"Found invalid ignore param for submodule "
									+ smw.getPath());
				}
				Repository subRepo = smw.getRepository();
				ObjectId subHead = subRepo.resolve("HEAD");
				if (subHead != null && !subHead.equals(smw.getObjectId()))
					modified.add(smw.getPath());
				else if (ignoreSubmoduleMode != IgnoreSubmoduleMode.DIRTY) {
					IndexDiff smid = submoduleIndexDiffs.get(smw.getPath());
					if (smid == null) {
						smid = new IndexDiff(subRepo
								wTreeIt.getWorkingTreeIterator(subRepo));
						submoduleIndexDiffs.put(smw.getPath()
					}
					if (smid.diff()) {
						if (ignoreSubmoduleMode == IgnoreSubmoduleMode.UNTRACKED
								&& smid.getAdded().isEmpty()
								&& smid.getChanged().isEmpty()
								&& smid.getConflicting().isEmpty()
								&& smid.getMissing().isEmpty()
								&& smid.getModified().isEmpty()
								&& smid.getRemoved().isEmpty()) {
							continue;
						}
						modified.add(smw.getPath());
