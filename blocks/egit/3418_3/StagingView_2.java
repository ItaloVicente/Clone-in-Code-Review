		if (selection.isEmpty())
			return;

		final DirCacheEditor edit;
		try {
			edit = currentRepository.lockDirCache().editor();
		} catch (IOException e) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
			return;
		}

		final RevTree headRevTree;
		try {
			final Ref head = currentRepository.getRef(Constants.HEAD);
			headRevTree = new RevWalk(currentRepository).parseTree(head.getObjectId());
		} catch (IOException e1) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
			return;
		}

		Iterator iterator = selection.iterator();
		while (iterator.hasNext()) {
			StagingEntry entry = (StagingEntry) iterator.next();
			switch(entry.getState()) {
			case ADDED:
			case CHANGED:
			case REMOVED:
				try {
					final TreeWalk tw = TreeWalk.forPath(currentRepository, entry.getPath(), headRevTree);
					if (tw != null) {
						edit.add(new DirCacheEditor.PathEdit(entry.getPath()) {
							@Override
							public void apply(DirCacheEntry ent) {
								ent.setFileMode(tw.getFileMode(0));
								ent.setObjectId(tw.getObjectId(0));
							}
						});
					}
				} catch (IOException e) {
					MessageDialog.openError(getSite().getShell(),
							UIText.CommitAction_MergeHeadErrorTitle,
							UIText.CommitAction_ErrorReadingMergeMsg);
				}
				break;
			default:
			}
		}

		try {
			edit.commit();
		} catch (IOException e) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
		}

		reload(currentRepository);
