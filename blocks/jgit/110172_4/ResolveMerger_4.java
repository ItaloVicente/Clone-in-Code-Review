			DirCacheEntry cacheEntry = entry.getValue();
			if (cacheEntry.getFileMode() == FileMode.GITLINK) {
				new File(entry.getKey()).mkdirs();
			} else {
				DirCacheCheckout.checkoutEntry(db
				modifiedFiles.add(entry.getKey());
			}
