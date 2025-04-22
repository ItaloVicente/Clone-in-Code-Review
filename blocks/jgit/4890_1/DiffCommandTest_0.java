	@Test
	public void testDiffWithPrefixes() throws Exception {
		write(new File(db.getWorkTree()
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(db.getWorkTree()

		OutputStream out = new ByteArrayOutputStream();
		git.diff().setOutputStream(out).setOldPrefix("old/")
				.setNewPrefix("new/")
				.call();

		String actual = out.toString();
		String expected = "diff --git old/test.txt new/test.txt\n"
				+ "index 30d74d2..4dba797 100644\n" + "--- old/test.txt\n"
				+ "+++ new/test.txt\n" + "@@ -1 +1 @@\n" + "-test\n"
				+ "\\ No newline at end of file\n" + "+test change\n"
				+ "\\ No newline at end of file\n";
		assertEquals(expected.toString()
	}

	@Test
	public void testDiffWithNegativeLineCount() throws Exception {
		write(new File(db.getWorkTree()
				"0\n1\n2\n3\n4\n5\n6\n7\n8\n9");
		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(db.getWorkTree()
				"0\n1\n2\n3\n4a\n5\n6\n7\n8\n9");

		OutputStream out = new ByteArrayOutputStream();
		git.diff().setOutputStream(out).setContextLines(1)
				.call();

		String actual = out.toString();
		String expected = "diff --git a/test.txt b/test.txt\n"
				+ "index f55b5c9..c5ec8fd 100644\n" + "--- a/test.txt\n"
				+ "+++ b/test.txt\n" + "@@ -4
				+ "+4a\n" + " 5\n";
		assertEquals(expected.toString()
	}

