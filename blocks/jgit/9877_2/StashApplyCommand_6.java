				DirCacheIterator dcIter = walk.getTree(1
						DirCacheIterator.class);
				if (dcIter != null && dcIter.idEqual(cIter)) {
					DirCacheEntry indexEntry = dcIter.getDirCacheEntry();
					entry.setLastModified(indexEntry.getLastModified());
					entry.setLength(indexEntry.getLength());
				}
