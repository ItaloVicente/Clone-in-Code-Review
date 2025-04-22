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
			return;
		}

		final DirCache dirCache;
		final DirCacheEditor edit;
		try {
			dirCache = currentRepository.lockDirCache();
			edit = dirCache.editor();
		} catch (IOException e) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
