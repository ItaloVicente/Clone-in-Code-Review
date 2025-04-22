			SubmoduleWalk smw = SubmoduleWalk.forIndex(repository);
			while (smw.next()) {
				try {
					if (localIgnoreSubmoduleMode == null)
						localIgnoreSubmoduleMode = smw.getModulesIgnore();
					if (IgnoreSubmoduleMode.ALL
							.equals(localIgnoreSubmoduleMode))
						continue;
				} catch (ConfigInvalidException e) {
					throw new IOException(MessageFormat.format(
							JGitText.get().invalidIgnoreParamSubmodule,
							smw.getPath()), e);
				}
				try (Repository subRepo = smw.getRepository()) {
					String subRepoPath = smw.getPath();
					if (subRepo != null) {
						if (subHead != null
								&& !subHead.equals(smw.getObjectId())) {
							modified.add(subRepoPath);
							recordFileMode(subRepoPath, FileMode.GITLINK);
						} else if (ignoreSubmoduleMode != IgnoreSubmoduleMode.DIRTY) {
							IndexDiff smid = submoduleIndexDiffs.get(smw
									.getPath());
							if (smid == null) {
								smid = new IndexDiff(subRepo,
										smw.getObjectId(),
										wTreeIt.getWorkingTreeIterator(subRepo));
								submoduleIndexDiffs.put(subRepoPath, smid);
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
