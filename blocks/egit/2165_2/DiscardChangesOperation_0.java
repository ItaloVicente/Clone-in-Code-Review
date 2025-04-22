		try {
			DirCacheEntry entry = dc.getEntry(resRelPath);
			File file = new File(res.getLocationURI());
			DirCacheCheckout.checkoutEntry(repository, file, entry);
		} finally {
			dc.unlock();
		}
