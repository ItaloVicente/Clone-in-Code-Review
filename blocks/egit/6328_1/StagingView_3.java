					final DirCache dirCache;
					final DirCacheEditor edit;
					try {
						dirCache = currentRepository.lockDirCache();
						edit = dirCache.editor();
					} catch (IOException e) {
						syncOpenError(getSite().getShell(),
								UIText.CommitAction_MergeHeadErrorTitle,
								UIText.CommitAction_ErrorReadingMergeMsg);
						return;
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
					syncUnlockUI();
				}
