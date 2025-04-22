			curs.release();
		}
		tw.setRecursive(true);
		if (tw.next()) {
			final DirCacheEntry newEntry = toEntry(stage, tw);
			beforeAdd(newEntry);
			fastAdd(newEntry);
			while (tw.next())
				fastAdd(toEntry(stage, tw));
