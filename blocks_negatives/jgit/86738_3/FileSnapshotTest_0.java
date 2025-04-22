	/**
	 * Create a file, wait long enough and verify that it has not been modified.
	 * 3.5 seconds mean any difference between file system timestamp and system
	 * clock should be significant.
	 *
	 * @throws Exception
	 */
	@Test
	public void testOldFile() throws Exception {
		File f1 = createFile("oldfile");
		waitNextSec(f1);
		FileSnapshot save = FileSnapshot.save(f1);
		Thread.sleep(3500);
		assertFalse(save.isModified(f1));
	}

