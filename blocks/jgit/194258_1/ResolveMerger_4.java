	private void checkout() throws NoWorkTreeException
		for (int i = toBeDeleted.size() - 1; i >= 0; i--) {
			String fileName = toBeDeleted.get(i);
			File f = new File(nonNullRepo().getWorkTree()
			if (!f.delete())
				if (!f.isDirectory())
					failingPaths.put(fileName
							MergeFailureReason.COULD_NOT_DELETE);
			modifiedFiles.add(fileName);
		}
		for (Map.Entry<String
				.entrySet()) {
			DirCacheEntry cacheEntry = entry.getValue();
			if (cacheEntry.getFileMode() == FileMode.GITLINK) {
				new File(nonNullRepo().getWorkTree()
			} else {
				DirCacheCheckout.checkoutEntry(db
						checkoutMetadata.get(entry.getKey()));
				modifiedFiles.add(entry.getKey());
			}
		}
	}

	protected void cleanUp() throws NoWorkTreeException
			CorruptObjectException
			IOException {
		if (inCore) {
			modifiedFiles.clear();
			return;
		}

		DirCache dc = nonNullRepo().readDirCache();
		Iterator<String> mpathsIt=modifiedFiles.iterator();
		while(mpathsIt.hasNext()) {
			String mpath = mpathsIt.next();
			DirCacheEntry entry = dc.getEntry(mpath);
			if (entry != null) {
				DirCacheCheckout.checkoutEntry(db
						cleanupMetadata.get(mpath));
			}
			mpathsIt.remove();
		}
	}
