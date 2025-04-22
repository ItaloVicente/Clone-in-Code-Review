	private void addToIndex(TreeSet<Long> modTimes)
			throws FileNotFoundException, IOException {
		DirCacheBuilder builder = db.lockDirCache().builder();
		FileTreeIterator fIt = new FileTreeIteratorWithTimeControl(
				db, modTimes);
		DirCacheEntry dce;
		while (!fIt.eof()) {
			dce = new DirCacheEntry(fIt.getEntryPathString());
			dce.setFileMode(fIt.getEntryFileMode());
			dce.setLastModified(fIt.getEntryLastModified());
			dce.setLength((int) fIt.getEntryLength());
			dce.setObjectId(fIt.getEntryObjectId());
			builder.add(dce);
			fIt.next(1);
		}
		builder.commit();
	}

