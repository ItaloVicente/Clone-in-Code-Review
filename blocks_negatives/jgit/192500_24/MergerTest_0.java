		MergeResult mergeResult = git.merge().include(renameCommit).setStrategy(strategy).call();
		assertEquals(mergeResult.getNewHead(), null);
		assertEquals(mergeResult.getMergeStatus(), MergeStatus.CONFLICTING);
		assertEquals(slightlyModifiedContent, read("test/file1"));
		assertEquals(originalContent, read("test/sub/file1"));

		assertEquals(
				"[test/file1, mode:100644, stage:1, content:a\nb\nc][test/file1, mode:100644, stage:2, content:a\nb\nb][test/sub/file1, mode:100644, content:a\nb\nc]",
				indexState(CONTENT));
		OutputStream out = new ByteArrayOutputStream();
		List<DiffEntry> entries = git.diff().setOutputStream(out).setOldTree(getTreeIterator("master"))
				.setNewTree(getTreeIterator("second-branch")).call();
		assertEquals(1, entries.size());
		assertEquals(ChangeType.RENAME, entries.get(0).getChangeType());

		assertEquals("test/file1", entries.get(0).getOldPath());
		assertEquals("test/sub/file1", entries.get(0).getNewPath());
		assertEquals("diff --git a/test/file1 b/test/sub/file1\n" + "similarity index 79%\n"
				+ "rename from test/file1\n" + "rename to test/sub/file1\n" + "index e8b9973..1c943a9 100644\n"
				+ "--- a/test/file1\n" + "+++ b/test/sub/file1\n" + "@@ -1,3 +1,3 @@\n" + " a\n" + " b\n" + "-b\n"
				+ "\\ No newline at end of file\n" + "+c\n" + "\\ No newline at end of file\n", out.toString());
