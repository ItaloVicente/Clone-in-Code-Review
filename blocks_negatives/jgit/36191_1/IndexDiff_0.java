				if (subHead != null && !subHead.equals(smw.getObjectId()))
					modified.add(smw.getPath());
				else if (ignoreSubmoduleMode != IgnoreSubmoduleMode.DIRTY) {
					IndexDiff smid = submoduleIndexDiffs.get(smw.getPath());
					if (smid == null) {
						smid = new IndexDiff(subRepo, smw.getObjectId(),
								wTreeIt.getWorkingTreeIterator(subRepo));
						submoduleIndexDiffs.put(smw.getPath(), smid);
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
