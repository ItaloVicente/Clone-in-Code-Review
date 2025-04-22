		DirCacheIterator di = tw.getTree(dirCache
		if (di != null) {
			DirCacheEntry dce = di.getDirCacheEntry();
			if (dce != null)
				if (dce.isAssumeValid())
					return false;
		}

