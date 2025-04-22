				Ref ref = repo.exactRef(oldName);
				if (ref == null) {
					ref = repo.exactRef(Constants.R_HEADS + oldName);
					Ref ref2 = repo.exactRef(Constants.R_REMOTES + oldName);
					if (ref != null && ref2 != null) {
						throw new RefNotFoundException(MessageFormat.format(
								JGitText.get().renameBranchFailedAmbiguous
								oldName
					} else if (ref == null) {
						if (ref2 != null) {
							ref = ref2;
						} else {
							throw new RefNotFoundException(MessageFormat.format(
									JGitText.get().refNotResolved
						}
					}
				}
