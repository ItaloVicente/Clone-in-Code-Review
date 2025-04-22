		try (ObjectReader objectReader = db.newObjectReader()) {
			DirCacheCheckout.checkoutEntry(db

			FileTreeIterator fti = new FileTreeIterator(trash
					db.getConfig().get(WorkingTreeOptions.KEY));
			while (!fti.getEntryPathString().equals("symlink"))
				fti.next(1);
			assertFalse(fti.isModified(dce
		}
