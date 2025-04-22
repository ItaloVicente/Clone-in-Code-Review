	@Test
	public void checkForCorrectIndex() throws Exception {
		File f;
		Git git = Git.wrap(db);

		f = writeTrashFiles(true
		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit firstCommit = git.commit().setMessage("initial commit")
				.call();
		assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(LENGTH | MOD_TIME | SMUDGE | CONTENT));

		f = writeTrashFiles(true
				null);
		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit masterCommit = git.commit().setMessage("master commit")
				.call();
		assertEquals(
				"[0
						+ "[1
						+ "[2
						+ "[3
						+ "[4
				indexState(LENGTH | MOD_TIME | SMUDGE | CONTENT));
		long lastIndexMod = db.getIndexFile().lastModified();

		git.checkout().setCreateBranch(true).setStartPoint(firstCommit)
				.setName("side").call();
		checkConsistentLastModified();
		checkModificationTimeStampOrder("1"
				"2"
				"<.git/index");

		assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(CONTENT));
		f = writeTrashFiles(true
		fsTick(f);
		git.add().addFilepattern(".").call();
		checkConsistentLastModified();
		checkModificationTimeStampOrder("0"
				"<.git/index");
		lastIndexMod = db.getIndexFile().lastModified();

		f = writeTrashFiles(true
		fsTick(f);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("side commit").call();
		assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(LENGTH | MOD_TIME | SMUDGE | CONTENT));
		checkConsistentLastModified();
		checkModificationTimeStampOrder("0"
				"2"
		lastIndexMod = db.getIndexFile().lastModified();

		git.merge().include(masterCommit).call();
		assertEquals(
				"[0
						+ "[1
						+ "[2
						+ "[3
						+ "[4
				indexState(CONTENT));
		checkModificationTimeStampOrder("0"
				"<2"
	}

	private void checkConsistentLastModified() throws IOException {
		DirCache dc = db.readDirCache();
		File workTree = db.getWorkTree();
		for (int i=0; i<dc.getEntryCount(); i++)
			assertEquals(
					"IndexEntry with path "
							+ dc.getEntry(i).getPathString()
							+ " has lastmodified with is different from the worktree file"
					new File(workTree
							.lastModified()
	}

	private void checkModificationTimeStampOrder(String... pathes) {
		long lastMod = Long.MIN_VALUE;
		for (String p : pathes) {
			boolean strong = p.startsWith("<");
			boolean fixed = p.charAt(strong ? 1 : 0) == '*';
			p = p.substring((strong ? 1 : 0) + (fixed ? 1 : 0));
			long curMod = fixed ? Long.valueOf(p).longValue() : new File(db.getWorkTree()
			if (strong)
				assertTrue("path " + p + " is not younger than predecesssor"
						curMod > lastMod);
			else
				assertTrue("path " + p + " is older than predecesssor"
						curMod >= lastMod);
		}
	}
