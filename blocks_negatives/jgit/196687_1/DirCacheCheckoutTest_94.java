	@Test(expected = CheckoutConflictException.class)
	public void testFolderFileConflict() throws Exception {
		RevCommit headCommit = commitFile("f/a", "initial content", "master");
		RevCommit checkoutCommit = commitFile("f/a", "side content", "side");
		FileUtils.delete(new File(db.getWorkTree(), "f"), FileUtils.RECURSIVE);
		writeTrashFile("f", "file instead of folder");
		new DirCacheCheckout(db, headCommit.getTree(), db.lockDirCache(),
				checkoutCommit.getTree()).checkout();
