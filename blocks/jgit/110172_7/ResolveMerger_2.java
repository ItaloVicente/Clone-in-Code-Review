			DirCacheEntry cacheEntry = entry.getValue();
			if (cacheEntry.getFileMode() == FileMode.GITLINK) {
				new File(nonNullRepo().getWorkTree()
			} else {
				DirCacheCheckout.checkoutEntry(db
				modifiedFiles.add(entry.getKey());
			}
