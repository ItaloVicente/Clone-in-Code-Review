	@Test
	public void testPathsResetWithUnmerged() throws Exception {
		setupRepository();

		String file = "a.txt";
		writeTrashFile(file

		git.add().addFilepattern(file).call();
		git.commit().setMessage("commit").call();

		DirCache index = db.lockDirCache();
		DirCacheBuilder builder = index.builder();
		builder.add(createEntry(file
		builder.add(createEntry(file
		builder.add(createEntry(file
		builder.add(createEntry("b.txt"
		assertTrue(builder.commit());

		assertEquals("[a.txt
				+ "[a.txt
				+ "[a.txt
				+ "[b.txt
				indexState(0));

		git.reset().addPath(file).call();

		assertEquals("[a.txt
				indexState(0));
	}

