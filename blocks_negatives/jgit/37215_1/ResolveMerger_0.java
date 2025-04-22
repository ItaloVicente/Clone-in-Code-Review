		for (Map.Entry<String, DirCacheEntry> entry : toBeCheckedOut
				.entrySet()) {
			File f = new File(db.getWorkTree(), entry.getKey());
			createDir(f.getParentFile());
			DirCacheCheckout.checkoutEntry(db, f, entry.getValue(), reader);
			modifiedFiles.add(entry.getKey());
		}
