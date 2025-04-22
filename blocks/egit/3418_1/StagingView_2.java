		if (selection.isEmpty())
			return;

		DirCache dirc;
		try {
			dirc = currentRepository.lockDirCache();
		} catch (IOException e) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
			return;
		}
		final DirCacheEditor edit = dirc.editor();

		final RevWalk walk = new RevWalk(currentRepository);
		final TreeWalk tw = new TreeWalk(currentRepository);

		try {
			Ref head = currentRepository.getRef(Constants.HEAD);

			ObjectId headId = head.getObjectId();
			tw.addTree(walk.parseTree(headId));
		} catch (IOException e1) {
			MessageDialog.openError(getSite().getShell(),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
		}

		Iterator iterator = selection.iterator();
		while (iterator.hasNext()) {
			StagingEntry entry = (StagingEntry) iterator.next();
			switch(entry.getState()) {
			case ADDED:
			case CHANGED:
				edit.add(new DirCacheEditor.DeletePath(entry.getPath()));
				break;
			case REMOVED:

				TreeFilter newFilter = PathFilter.create(entry.getPath());
				tw.setFilter(newFilter);

				try {
					if (tw.next()) {
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
