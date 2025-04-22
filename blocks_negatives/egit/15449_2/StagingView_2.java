			try {
				final TreeWalk tw = TreeWalk.forPath(currentRepository,
						entry.getPath(), headRev.getTree());
				if (tw != null)
					edit.add(new DirCacheEditor.PathEdit(entry.getPath()) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.setFileMode(tw.getFileMode(0));
							ent.setObjectId(tw.getObjectId(0));
							ent.setLastModified(0);
						}
					});
			} catch (IOException e) {
				MessageDialog.openError(getSite().getShell(),
						UIText.CommitAction_MergeHeadErrorTitle,
						UIText.CommitAction_ErrorReadingMergeMsg);
			}
			break;
