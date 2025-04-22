	@Test
	public void checkForCorrectIndex() throws Exception {
		File f;
		Git git = Git.wrap(db);

		f = writeTrashFiles(true
		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit firstCommit = git.commit().setMessage("initial commit")
				.call();
		Assert.assertEquals("[0
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
		Assert.assertEquals(
				"[0
						+ "[1
						+ "[2
						+ "[3
						+ "[4
				indexState(LENGTH | MOD_TIME | SMUDGE | CONTENT));

		git.checkout().setCreateBranch(true).setStartPoint(firstCommit)
				.setName("side").call();
		Assert.assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(CONTENT));
		f = writeTrashFiles(true
		fsTick(f);
		git.add().addFilepattern(".").call();

		f = writeTrashFiles(true
		fsTick(f);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("side commit").call();
		Assert.assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(LENGTH | MOD_TIME | SMUDGE | CONTENT));

		git.merge().include(masterCommit).call();
		Assert.assertEquals(
				"[0
						+ "[1
						+ "[2
						+ "[3
						+ "[4
				indexState(CONTENT));

		DirCache dc=db.readDirCache();
		Assert.assertTrue(db.getIndexFile().lastModified() >= dc.getEntry("2")
				.getLastModified());
		Assert.assertTrue(dc.getEntry("2").getLastModified() > dc.getEntry("1")
				.getLastModified());
		Assert.assertTrue(dc.getEntry("1").getLastModified() > dc.getEntry("0")
				.getLastModified());
		Assert.assertTrue(dc.getEntry("0").getLastModified() > dc.getEntry("4")
				.getLastModified());
	}
