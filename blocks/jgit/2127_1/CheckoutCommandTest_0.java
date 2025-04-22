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

	public void testCheckoutWithUndeletedFiles() throws Exception {
		String osname = System.getProperty("os.name");
		if (!osname.startsWith("Windows"))
			return;
		CheckoutCommand co = git.checkout();
		File testFile = new File(db.getWorkTree()
		assertTrue(testFile.exists());
		FileUtils.delete(testFile);
		assertFalse(testFile.exists());
		git.add().addFilepattern("Test.txt");
		git.commit().setMessage("Delete Test.txt").setAll(true).call();
		git.checkout().setName("master").call();
		assertTrue(testFile.exists());
		FileInputStream fis = new FileInputStream(testFile);
		try {
			assertEquals(Status.NOT_TRIED
			co.setName("test").call();
			assertTrue(testFile.exists());
			assertEquals(Status.UNDELETEDFILES
			assertTrue(co.getResult().getUndeletedList().contains("Test.txt"));
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			fis.close();
		}
	}
