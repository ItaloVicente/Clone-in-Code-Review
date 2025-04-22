
				if (dcTree != null && fTree != null) {
					final DirCacheEntry entry = dcTree.getDirCacheEntry();
					if (entry.isSmudged()) {
						final long size = fTree.getEntryLength();
						final long mTime = fTree.getEntryLastModified();
						dcEditor.add(new PathEdit(path) {

							@Override
							public void apply(DirCacheEntry ent) {
								ent.setLength(size);
								ent.setLastModified(mTime);
							}
						});
					}
				}
