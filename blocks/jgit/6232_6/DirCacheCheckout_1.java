				else {
					DirCacheEntry entry = i.getDirCacheEntry();
					if (entry.getLastModified() == 0)
						entry.setLastModified(f.getEntryLastModified());
					keep(entry);
				}
