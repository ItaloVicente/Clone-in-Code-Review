		DirCacheEntry ourDce = null;

		if (index == null || index.getDirCacheEntry() == null) {
			if (nonTree(modeO)) {
				ourDce = new DirCacheEntry(tw.getRawPath());
				ourDce.setObjectId(tw.getObjectId(T_OURS));
				ourDce.setFileMode(tw.getFileMode(T_OURS));
			}
		} else {
			ourDce = index.getDirCacheEntry();
		}

