	@Test
	public void testCheckoutAutoCrlfTrue() throws Exception {
		int nrOfAutoCrlfTestFiles = 200;

		FileBasedConfig c = db.getConfig();
		c.setString("core"
		c.save();

		AddCommand add = git.add();
		for (int i = 100; i < 100 + nrOfAutoCrlfTestFiles; i++) {
			writeTrashFile("Test_" + i + ".txt"
					+ " world\nX\nYU\nJK\n");
			add.addFilepattern("Test_" + i + ".txt");
		}
		fsTick(null);
		add.call();
		RevCommit c1 = git.commit().setMessage("add some lines").call();

		add = git.add();
		for (int i = 100; i < 100 + nrOfAutoCrlfTestFiles; i++) {
			writeTrashFile("Test_" + i + ".txt"
					+ " world\nX\nY\n");
			add.addFilepattern("Test_" + i + ".txt");
		}
		fsTick(null);
		add.call();
		git.commit().setMessage("add more").call();

		git.checkout().setName(c1.getName()).call();

		boolean foundUnsmudged = false;
		DirCache dc = db.readDirCache();
		for (int i = 100; i < 100 + nrOfAutoCrlfTestFiles; i++) {
			DirCacheEntry entry = dc.getEntry(
					"Test_" + i + ".txt");
			if (!entry.isSmudged()) {
				foundUnsmudged = true;
				assertEquals("unexpected file length in git index"
						entry.getLength());
			}
		}
		org.junit.Assume.assumeTrue(foundUnsmudged);
	}
