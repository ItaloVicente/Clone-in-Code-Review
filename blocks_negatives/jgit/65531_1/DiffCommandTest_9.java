		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(db.getWorkTree(), "test.txt"), "test change");

		OutputStream out = new ByteArrayOutputStream();
		git.diff().setOutputStream(out).setSourcePrefix("old/")
				.setDestinationPrefix("new/")
				.call();

		String actual = out.toString();
		String expected = "diff --git old/test.txt new/test.txt\n"
				+ "index 30d74d2..4dba797 100644\n" + "--- old/test.txt\n"
				+ "+++ new/test.txt\n" + "@@ -1 +1 @@\n" + "-test\n"
				+ "\\ No newline at end of file\n" + "+test change\n"
				+ "\\ No newline at end of file\n";
		assertEquals(expected.toString(), actual);
