		DirCacheEntry ourDce = null;

		if (index == null) {
			if (modeO != 0) {
				ourDce = new DirCacheEntry(tw.getRawPath());
				ourDce.setObjectId(tw.getObjectId(T_OURS));
				ourDce.setFileMode(tw.getFileMode(T_OURS));
			}
		} else {
			ourDce = index.getDirCacheEntry();
		}

