	private AbstractTreeIterator getTreeIterator(String name) throws IOException {
		final ObjectId id = db.resolve(name);
		if (id == null)
			throw new IllegalArgumentException(name);
		final CanonicalTreeParser p = new CanonicalTreeParser();
		try (ObjectReader or = db.newObjectReader(); RevWalk rw = new RevWalk(db)) {
			p.reset(or
			return p;
		}
	}

	@Theory
	public void checkRenameSubDir_modifyConflict(MergeStrategy strategy) throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		writeTrashFile("test/file1"
		git.add().addFilepattern("test/file1").call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("second-branch").call();
		git.rm().addFilepattern("test/file1").call();
		writeTrashFile("test/sub/file1"
		git.add().addFilepattern("test/sub/file1").call();
		RevCommit renameCommit = git.commit().setMessage("Rename file").call();

		git.checkout().setName("master").call();
		writeTrashFile("test/file1"
		git.add().addFilepattern("test/file1").call();

		RevCommit modifyContentCommit = git.commit().setMessage("Commit slightly modified content").call();

		MergeResult mergeResult = git.merge().include(renameCommit).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead()
		assertEquals(mergeResult.getMergeStatus()
		assertEquals(slightlyModifiedContent
		assertEquals(originalContent

		assertEquals(
				"[test/file1
				indexState(CONTENT));
		OutputStream out = new ByteArrayOutputStream();
		List<DiffEntry> entries = git.diff().setOutputStream(out).setOldTree(getTreeIterator("master"))
				.setNewTree(getTreeIterator("second-branch")).call();
		assertEquals(1
		assertEquals(ChangeType.RENAME

		assertEquals("test/file1"
		assertEquals("test/sub/file1"
		assertEquals("diff --git a/test/file1 b/test/sub/file1\n" + "similarity index 79%\n"
				+ "rename from test/file1\n" + "rename to test/sub/file1\n" + "index e8b9973..1c943a9 100644\n"
				+ "--- a/test/file1\n" + "+++ b/test/sub/file1\n" + "@@ -1
				+ "\\ No newline at end of file\n" + "+c\n" + "\\ No newline at end of file\n"

	}

	@Theory
	public void checkRenameSubDir_renameOnly_noConflict(MergeStrategy strategy) throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		writeTrashFile("test/file1"
		git.add().addFilepattern("test/file1").call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("second-branch").call();
		git.rm().addFilepattern("test/file1").call();
		writeTrashFile("test/sub/file1"
		git.add().addFilepattern("test/sub/file1").call();
		RevCommit renameCommit = git.commit().setMessage("Rename file").call();

		git.checkout().setName("master").call();
		writeTrashFile("test/file1"
		git.add().addFilepattern("test/file1").call();

		RevCommit modifyContentCommit = git.commit().setMessage("Commit same content").call();

		MergeResult mergeResult = git.merge().include(renameCommit).setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus()
		assertEquals(originalContent

		assertEquals("[test/sub/file1
		OutputStream out = new ByteArrayOutputStream();
		List<DiffEntry> entries = git.diff().setOutputStream(out).setOldTree(getTreeIterator("master"))
				.setNewTree(getTreeIterator("second-branch")).call();
		assertEquals(1
		assertEquals(ChangeType.RENAME

		assertEquals("test/file1"
		assertEquals("test/sub/file1"
		assertEquals(""

	}

