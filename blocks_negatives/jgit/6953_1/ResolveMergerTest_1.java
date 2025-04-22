	@Test
	public void checkLockedFilesToBeDeleted() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("a.txt", "orig");
		writeTrashFile("b.txt", "orig");
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		RevCommit first = git.commit().setMessage("added a.txt, b.txt").call();

		writeTrashFile("a.txt", "master");
		git.rm().addFilepattern("b.txt").call();
		RevCommit masterCommit = git.commit()
				.setMessage("modified a.txt, deleted b.txt").setAll(true)
				.call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("c.txt", "side");
		git.add().addFilepattern("c.txt").call();
		git.commit().setMessage("added c.txt").call();

		FileInputStream fis = new FileInputStream(new File(db.getWorkTree(),
				"b.txt"));
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		if (mergeRes.getMergeStatus().equals(MergeStatus.FAILED)) {
			assertEquals(1, mergeRes.getFailingPaths().size());
			assertEquals(MergeFailureReason.COULD_NOT_DELETE, mergeRes
					.getFailingPaths().get("b.txt"));
		}
		assertEquals("[a.txt, mode:100644, content:master]"
				+ "[c.txt, mode:100644, content:side]", indexState(CONTENT));
		fis.close();
	}

	@Test
	public void checkForCorrectIndex() throws Exception {
		File f;
		long lastTs4, lastTsIndex;
		Git git = Git.wrap(db);
		File indexFile = db.getIndexFile();

		f = writeTrashFiles(false, "orig", "orig", "1\n2\n3", "orig", "orig");
		lastTs4 = f.lastModified();

		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit firstCommit = git.commit().setMessage("initial commit")
				.call();
		checkConsistentLastModified("0", "1", "2", "3", "4");
		checkModificationTimeStampOrder("1", "2", "3", "4", "<.git/index");
		assertEquals("Commit should not touch working tree file 4", lastTs4,
				new File(db.getWorkTree(), "4").lastModified());
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		f = writeTrashFiles(false, "master", null, "1master\n2\n3", "master",
				null);
		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit masterCommit = git.commit().setMessage("master commit")
				.call();
		checkConsistentLastModified("0", "1", "2", "3", "4");
		checkModificationTimeStampOrder("1", "4", "*" + lastTs4, "<*"
				+ lastTsIndex, "<0",
				"2", "3", "<.git/index");
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		git.checkout().setCreateBranch(true).setStartPoint(firstCommit)
				.setName("side").call();
		checkConsistentLastModified("0", "1", "2", "3", "4");
		checkModificationTimeStampOrder("1", "4", "*" + lastTs4, "<*"
				+ lastTsIndex, "<0",
				"2", "3", ".git/index");
		lastTsIndex = indexFile.lastModified();

		assertEquals("[0, mode:100644, content:orig]" //
				+ "[1, mode:100644, content:orig]" //
				+ "[2, mode:100644, content:1\n2\n3]" //
				+ "[3, mode:100644, content:orig]" //
				+ "[4, mode:100644, content:orig]", //
				indexState(CONTENT));
		fsTick(indexFile);
		f = writeTrashFiles(false, "orig", "orig", "1\n2\n3", "orig", "orig");
		lastTs4 = f.lastModified();
		fsTick(f);
		git.add().addFilepattern(".").call();
		checkConsistentLastModified("0", "1", "2", "3", "4");
		checkModificationTimeStampOrder("*" + lastTsIndex, "<0", "1", "2", "3",
				"4", "<.git/index");
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		f = writeTrashFiles(false, null, "side", "1\n2\n3side", "side", null);
		fsTick(f);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("side commit").call();
		checkConsistentLastModified("0", "1", "2", "3", "4");
		checkModificationTimeStampOrder("0", "4", "*" + lastTs4, "<*"
				+ lastTsIndex, "<1", "2", "3", "<.git/index");
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		git.merge().include(masterCommit).call();
		checkConsistentLastModified("0", "1", "2", "4");
		checkModificationTimeStampOrder("4", "*" + lastTs4, "<1", "<*"
				+ lastTsIndex, "<0", "2", "3", ".git/index");
		assertEquals(
				"[0, mode:100644, content:master]" //
						+ "[1, mode:100644, content:side]" //
						+ "[2, mode:100644, content:1master\n2\n3side\n]" //
						+ "[3, mode:100644, stage:1, content:orig][3, mode:100644, stage:2, content:side][3, mode:100644, stage:3, content:master]" //
						+ "[4, mode:100644, content:orig]", //
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
							+ " has lastmodified with is different from the worktree file",
					new File(workTree, path).lastModified(), dc.getEntry(path)
							.getLastModified());
	}

	private void checkModificationTimeStampOrder(String... pathes) {
		long lastMod = Long.MIN_VALUE;
		for (String p : pathes) {
			boolean strong = p.startsWith("<");
			boolean fixed = p.charAt(strong ? 1 : 0) == '*';
			p = p.substring((strong ? 1 : 0) + (fixed ? 1 : 0));
			long curMod = fixed ? Long.valueOf(p).longValue() : new File(
					db.getWorkTree(), p).lastModified();
			if (strong)
				assertTrue("path " + p + " is not younger than predecesssor",
						curMod > lastMod);
			else
				assertTrue("path " + p + " is older than predecesssor",
						curMod >= lastMod);
		}
	}
