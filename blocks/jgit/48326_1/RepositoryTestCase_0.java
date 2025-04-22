		try (ObjectInserter inserter = db.newObjectInserter()) {
			DirCacheBuilder builder = db.lockDirCache().builder();
			DirCacheEntry dce;

			while (!treeItr.eof()) {
				long len = treeItr.getEntryLength();

				dce = new DirCacheEntry(treeItr.getEntryPathString());
				dce.setFileMode(treeItr.getEntryFileMode());
				dce.setLastModified(treeItr.getEntryLastModified());
				dce.setLength((int) len);
				FileInputStream in = new FileInputStream(
						treeItr.getEntryFile());
				dce.setObjectId(inserter.insert(Constants.OBJ_BLOB
				in.close();
				builder.add(dce);
				treeItr.next(1);
			}
			builder.commit();
			inserter.flush();
