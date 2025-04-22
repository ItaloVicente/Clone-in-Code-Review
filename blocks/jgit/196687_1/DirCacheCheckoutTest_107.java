	@Test
	void testFolderFileConflict() throws Exception {
		assertThrows(CheckoutConflictException.class
			RevCommit headCommit = commitFile("f/a"
			RevCommit checkoutCommit = commitFile("f/a"
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("f"
			new DirCacheCheckout(db
					checkoutCommit.getTree()).checkout();
		});
