		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(folder, "folder.txt"), "folder change");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("second commit").call();
		write(new File(folder, "folder.txt"), "second folder change");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("third commit").call();

		DiffCommand diff = git.diff().setShowNameAndStatusOnly(true)
				.setPathFilter(PathFilter.create("test.txt"))
				.setOldTree(getTreeIterator("HEAD^^"))
				.setNewTree(getTreeIterator("HEAD^"));
		List<DiffEntry> entries = diff.call();
		assertEquals(0, entries.size());

		OutputStream out = new ByteArrayOutputStream();
		diff = git.diff().setOutputStream(out)
				.setOldTree(getTreeIterator("HEAD^^"))
				.setNewTree(getTreeIterator("HEAD^"));
		entries = diff.call();
		assertEquals(1, entries.size());
		assertEquals(ChangeType.MODIFY, entries.get(0).getChangeType());
		assertEquals("folder/folder.txt", entries.get(0).getOldPath());
		assertEquals("folder/folder.txt", entries.get(0).getNewPath());

		String actual = out.toString();
		String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
				+ "index 0119635..95c4c65 100644\n"
				+ "--- a/folder/folder.txt\n"
				+ "+++ b/folder/folder.txt\n"
				+ "@@ -1 +1 @@\n"
				+ "-folder\n"
				+ "\\ No newline at end of file\n"
				+ "+folder change\n"
				+ "\\ No newline at end of file\n";
		assertEquals(expected.toString(), actual);
