		ObjectReader objectReader = db.newObjectReader();
		DirCacheCheckout.checkoutEntry(db, dce, objectReader);

		FileTreeIterator fti = new FileTreeIterator(trash, db.getFS(), db
				.getConfig().get(WorkingTreeOptions.KEY));
		while (!fti.getEntryPathString().equals("symlink"))
			fti.next(1);
		assertFalse(fti.isModified(dce, false, objectReader));
		objectReader.release();
