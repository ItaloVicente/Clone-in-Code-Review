				try {

					RevCommit headRev = null;
					try {
						final Ref head = currentRepository.getRef(Constants.HEAD);
						if (head.getObjectId() != null)
							headRev = new RevWalk(currentRepository).parseCommit(head
									.getObjectId());
					} catch (IOException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.CommitAction_MergeHeadErrorTitle,
								UIText.CommitAction_ErrorReadingMergeMsg);
						return new Status(IStatus.ERROR,
								Activator.getPluginId(),
								UIText.CommitAction_ErrorReadingMergeMsg);
					}

					final DirCache dirCache;
					final DirCacheEditor edit;
					try {
						dirCache = currentRepository.lockDirCache();
						edit = dirCache.editor();
					} catch (IOException e) {
						syncOpenError(getSite().getShell(),
								UIText.CommitAction_MergeHeadErrorTitle,
								UIText.CommitAction_ErrorReadingMergeMsg);
						return new Status(IStatus.ERROR,
								Activator.getPluginId(),
								UIText.CommitAction_ErrorReadingMergeMsg);
					}

					try {
						updateDirCache(selection, headRev, edit);

						try {
							edit.commit();
						} catch (IOException e) {
							syncOpenError(getSite().getShell(),
									UIText.CommitAction_MergeHeadErrorTitle,
									UIText.CommitAction_ErrorReadingMergeMsg);
						}
					} finally {
						dirCache.unlock();
					}
				} finally {
					asyncUnlockUI();
				}
				return Status.OK_STATUS;
