
						if (indexIter == null && headIter == null)
							untracked.add(entry);
						else
							wtEdits.add(new PathEdit(entry) {
								public void apply(DirCacheEntry ent) {
									ent.copyMetaData(entry);
								}
							});
