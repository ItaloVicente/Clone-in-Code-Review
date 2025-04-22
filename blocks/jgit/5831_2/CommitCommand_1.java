
				if (dcTree != null && fTree != null) {
					final DirCacheEntry entry = dcTree.getDirCacheEntry();
					if (entry.isSmudged()) {
						entry.setLength(fTree.getEntryLength());
						entry.setLastModified(fTree.getEntryLastModified());
					}
				}
