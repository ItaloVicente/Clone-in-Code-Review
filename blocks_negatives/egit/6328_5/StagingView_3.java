		final DirCache dirCache;
		final DirCacheEditor edit;
		try {
			dirCache = currentRepository.lockDirCache();
			edit = dirCache.editor();
		} catch (IOException e) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
			return;
		}
