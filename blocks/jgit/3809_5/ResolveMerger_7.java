		ObjectReader r = db.getObjectDatabase().newReader();
		try {
			for (Map.Entry<String
					.entrySet()) {
				File f = new File(db.getWorkTree()
				if (entry.getValue() != null) {
					createDir(f.getParentFile());
					DirCacheCheckout.checkoutEntry(db
				} else {
					if (!f.delete())
						failingPaths.put(entry.getKey()
								MergeFailureReason.COULD_NOT_DELETE);
				}
				modifiedFiles.add(entry.getKey());
