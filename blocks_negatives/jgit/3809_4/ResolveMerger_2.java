		for (Map.Entry<String, DirCacheEntry> entry : toBeCheckedOut.entrySet()) {
			File f = new File(db.getWorkTree(), entry.getKey());
			if (entry.getValue() != null) {
				createDir(f.getParentFile());
				DirCacheCheckout.checkoutEntry(db,
						f,
						entry.getValue());
			} else {
				if (!f.delete())
					failingPaths.put(entry.getKey(),
							MergeFailureReason.COULD_NOT_DELETE);
