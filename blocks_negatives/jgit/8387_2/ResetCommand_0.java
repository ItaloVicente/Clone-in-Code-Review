				if (tree == null)
					edit.add(new DirCacheEditor.DeletePath(path));
					final FileMode entryFileMode = tree.getEntryFileMode();
					final ObjectId entryObjectId = tree.getEntryObjectId();
					edit.add(new DirCacheEditor.PathEdit(path) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.setFileMode(entryFileMode);
							ent.setObjectId(entryObjectId);
							ent.setLastModified(0);
						}
					});
