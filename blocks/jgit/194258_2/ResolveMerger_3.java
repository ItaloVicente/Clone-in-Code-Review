		if (implicitDirCache) {
			dircache = nonNullRepo().lockDirCache();
		}
		if (!inCore) {
			checkoutMetadata = new HashMap<>();
			cleanupMetadata = new HashMap<>();
		}
		try {
			return mergeTrees(mergeBase()
					false);
		} finally {
			checkoutMetadata = null;
			cleanupMetadata = null;
			if (implicitDirCache) {
				dircache.unlock();
			}
		}
