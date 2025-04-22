	@Theory
	public void checkRenameSubDir_renameOnly_noConflict(MergeStrategy strategy) throws Exception {
		if (!strategy.equals(MergeStrategy.RECURSIVE)) {
			return;
		}

		Git git = Git.wrap(db);
		String originalContent = "a\nb\nc";
		String slightlyModifiedContent = "a\nb\nb";
		writeTrashFile("test/file1", originalContent);
		git.add().addFilepattern("test/file1").call();
		RevCommit commitI = git.commit().setMessage("Initial commit").call();

		git.checkout().setCreateBranch(true).setStartPoint(commitI).setName("second-branch").call();
		git.rm().addFilepattern("test/file1").call();
		writeTrashFile("test/sub/file1", originalContent);
		git.add().addFilepattern("test/sub/file1").call();
		RevCommit renameCommit = git.commit().setMessage("Rename file").call();

		git.checkout().setName("master").call();
		writeTrashFile("test/file1", originalContent);
		git.add().addFilepattern("test/file1").call();

		RevCommit modifyContentCommit = git.commit().setMessage("Commit same content").call();

		MergeResult mergeResult = git.merge().include(renameCommit).setStrategy(strategy).call();
		assertEquals(mergeResult.getMergeStatus(), MergeStatus.MERGED);
		assertEquals(originalContent, read("test/sub/file1"));

		assertEquals("[test/sub/file1, mode:100644, content:a\nb\nc]", indexState(CONTENT));
		OutputStream out = new ByteArrayOutputStream();
		List<DiffEntry> entries = git.diff().setOutputStream(out).setOldTree(getTreeIterator("master"))
				.setNewTree(getTreeIterator("second-branch")).call();
		assertEquals(1, entries.size());
		assertEquals(ChangeType.RENAME, entries.get(0).getChangeType());

		assertEquals("test/file1", entries.get(0).getOldPath());
		assertEquals("test/sub/file1", entries.get(0).getNewPath());
		assertEquals("", out.toString());

	}

