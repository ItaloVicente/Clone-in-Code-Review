		for (Map.Entry<String
				.entrySet()) {
			File f = new File(db.getWorkTree()
			createDir(f.getParentFile());
			DirCacheCheckout.checkoutEntry(db
			modifiedFiles.add(entry.getKey());
		}
		for (int i = toBeDeleted.size() - 1; i >= 0; i--) {
			String fileName = toBeDeleted.get(i);
			File f = new File(db.getWorkTree()
			if (!f.delete())
				if (!f.isDirectory())
					failingPaths.put(fileName
							MergeFailureReason.COULD_NOT_DELETE);
			modifiedFiles.add(fileName);
