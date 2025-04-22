		DirCache dc = repository.lockDirCache();
		try {
			DirCacheEntry entry = dc.getEntry(resRelPath);
			if (entry != null) {
				File file = new File(res.getLocationURI());
				DirCacheCheckout.checkoutEntry(repository, file, entry);
			}
		} finally {
			dc.unlock();
		}
