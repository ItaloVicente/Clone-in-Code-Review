			final DirCacheEntry[] sEnt = sCache.getEntriesWithin(sPath);
			if (sEnt.length == 0) {
				sCache.unlock();
				return false;
			}

			final DirCacheEditor sEdit = sCache.editor();
			sEdit.add(new DirCacheEditor.DeleteTree(sPath));
