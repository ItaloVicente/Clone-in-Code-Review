	@Test
	public void testMixedResetWithUnmerged() throws Exception {
		git = new Git(db);

		String file = "a.txt";
		writeTrashFile(file
		String file2 = "b.txt";
		writeTrashFile(file2

		git.add().addFilepattern(file).addFilepattern(file2).call();
		git.commit().setMessage("commit").call();

		DirCache index = db.lockDirCache();
		DirCacheBuilder builder = index.builder();
		builder.add(createEntry(file
		builder.add(createEntry(file
		builder.add(createEntry(file
		assertTrue(builder.commit());

		assertEquals("[a.txt
				+ "[a.txt
				+ "[a.txt
				indexState(0));

		git.reset().setMode(ResetType.MIXED).call();

		assertEquals("[a.txt
				indexState(0));
	}

