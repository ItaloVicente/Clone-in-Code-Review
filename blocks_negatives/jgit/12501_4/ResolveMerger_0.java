		ObjectReader r = db.getObjectDatabase().newReader();
		try {
			for (Map.Entry<String, DirCacheEntry> entry : toBeCheckedOut
					.entrySet()) {
				File f = new File(db.getWorkTree(), entry.getKey());
				createDir(f.getParentFile());
				DirCacheCheckout.checkoutEntry(db, f, entry.getValue(), r);
				modifiedFiles.add(entry.getKey());
			}
			for (int i = toBeDeleted.size() - 1; i >= 0; i--) {
				String fileName = toBeDeleted.get(i);
				File f = new File(db.getWorkTree(), fileName);
				if (!f.delete())
					failingPaths.put(fileName,
							MergeFailureReason.COULD_NOT_DELETE);
				modifiedFiles.add(fileName);
			}
		} finally {
			r.release();
