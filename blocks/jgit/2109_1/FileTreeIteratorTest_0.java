	public void testIsModifiedSymlink() throws Exception {
		File f = writeTrashFile("symlink"
		Git git = new Git(db);
		git.add().addFilepattern("symlink").call();
		git.commit().setMessage("commit").call();

		DirCacheEntry dce = db.readDirCache().getEntry("symlink");
		dce.setFileMode(FileMode.SYMLINK);
		DirCacheCheckout.checkoutEntry(db

		FileTreeIterator fti = new FileTreeIterator(trash
				.getConfig().get(WorkingTreeOptions.KEY));
		while (!fti.getEntryPathString().equals("symlink"))
			fti.next(1);
		assertFalse(fti.isModified(dce
	}

