	@Test
	public void checkForCorrectIndex() throws Exception {
		File f;
		long lastTs4
		Git git = Git.wrap(db);
		File indexFile = db.getIndexFile();

		f = writeTrashFiles(false
		lastTs4 = f.lastModified();

		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit firstCommit = git.commit().setMessage("initial commit")
				.call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
		assertEquals("Commit should not touch working tree file 4"
				new File(db.getWorkTree()
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		f = writeTrashFiles(false
				null);
		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit masterCommit = git.commit().setMessage("master commit")
				.call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
				+ lastTsIndex
				"2"
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		git.checkout().setCreateBranch(true).setStartPoint(firstCommit)
				.setName("side").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
				+ lastTsIndex
				"2"
		lastTsIndex = indexFile.lastModified();

		assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(CONTENT));
		fsTick(indexFile);
		f = writeTrashFiles(false
		lastTs4 = f.lastModified();
		fsTick(f);
		git.add().addFilepattern(".").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("*" + lastTsIndex
				"4"
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		f = writeTrashFiles(false
		fsTick(f);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("side commit").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("0"
				+ lastTsIndex
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		git.merge().include(masterCommit).call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("4"
				+ lastTsIndex
		assertEquals(
				"[0
						+ "[1
						+ "[2
						+ "[3
						+ "[4
				indexState(CONTENT));
	}

	private void checkConsistentLastModified(String... pathes)
			throws IOException {
		DirCache dc = db.readDirCache();
		File workTree = db.getWorkTree();
		for (String path : pathes)
			assertEquals(
					"IndexEntry with path "
							+ path
							+ " has lastmodified with is different from the worktree file"
					new File(workTree
							.getLastModified());
	}

	private void checkModificationTimeStampOrder(String... pathes) {
		long lastMod = Long.MIN_VALUE;
		for (String p : pathes) {
			boolean strong = p.startsWith("<");
			boolean fixed = p.charAt(strong ? 1 : 0) == '*';
			p = p.substring((strong ? 1 : 0) + (fixed ? 1 : 0));
			long curMod = fixed ? Long.valueOf(p).longValue() : new File(
					db.getWorkTree()
			if (strong)
				assertTrue("path " + p + " is not younger than predecesssor"
						curMod > lastMod);
			else
				assertTrue("path " + p + " is older than predecesssor"
						curMod >= lastMod);
		}
	}
