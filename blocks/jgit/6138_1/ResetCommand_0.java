			DirCacheEditor editor = dc.editor();

			walk = new TreeWalk(repo);
			walk.addTree(commit.getTree());
			walk.addTree(new DirCacheIterator(dc));
			walk.setRecursive(true);

			while (walk.next()) {
				AbstractTreeIterator cIter = walk.getTree(0
						AbstractTreeIterator.class);
				if (cIter == null) {
					editor.add(new DeletePath(walk.getPathString()));
					continue;
				}

				final DirCacheEntry entry = new DirCacheEntry(walk.getRawPath());
				entry.setFileMode(cIter.getEntryFileMode());
				entry.setObjectIdFromRaw(cIter.idBuffer()

				DirCacheIterator dcIter = walk.getTree(1
						DirCacheIterator.class);
				if (dcIter != null && dcIter.idEqual(cIter)) {
					DirCacheEntry indexEntry = dcIter.getDirCacheEntry();
					entry.setLastModified(indexEntry.getLastModified());
					entry.setLength(indexEntry.getLength());
				}

				editor.add(new PathEdit(entry) {

					@Override
					public void apply(DirCacheEntry ent) {
						ent.copyMetaData(entry);
					}
				});
			}

			editor.commit();
