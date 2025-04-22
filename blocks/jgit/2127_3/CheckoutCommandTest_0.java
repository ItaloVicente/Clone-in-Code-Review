	public void testCheckoutWithConflict() {
		CheckoutCommand co = git.checkout();
		try {
			writeTrashFile("Test.txt"
			assertEquals(Status.NOT_TRIED
			co.setName("master").call();
			fail("Should have failed");
		} catch (Exception e) {
			assertEquals(Status.CONFLICTS
			assertTrue(co.getResult().getConflictList().contains("Test.txt"));
		}
	}

	public void testCheckoutWithNonDeletedFiles() throws Exception {
		File testFile = writeTrashFile("temp"
		FileInputStream fis = new FileInputStream(testFile);
		try {
			FileUtils.delete(testFile);
			return;
		} catch (IOException e) {
		}
		fis.close();
		FileUtils.delete(testFile);
		CheckoutCommand co = git.checkout();
		testFile = new File(db.getWorkTree()
		assertTrue(testFile.exists());
		FileUtils.delete(testFile);
		assertFalse(testFile.exists());
		git.add().addFilepattern("Test.txt");
		git.commit().setMessage("Delete Test.txt").setAll(true).call();
		git.checkout().setName("master").call();
		assertTrue(testFile.exists());
		fis = new FileInputStream(testFile);
		try {
			assertEquals(Status.NOT_TRIED
			co.setName("test").call();
			assertTrue(testFile.exists());
			assertEquals(Status.NONDELETED
			assertTrue(co.getResult().getUndeletedList().contains("Test.txt"));
		} finally {
			fis.close();
		}
	}
