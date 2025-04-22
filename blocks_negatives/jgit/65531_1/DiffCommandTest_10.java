		Git git = new Git(db);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("Initial commit").call();
		write(new File(db.getWorkTree(), "test.txt"),
				"0\n1\n2\n3\n4a\n5\n6\n7\n8\n9");

		OutputStream out = new ByteArrayOutputStream();
		git.diff().setOutputStream(out).setContextLines(1)
				.call();

		String actual = out.toString();
		String expected = "diff --git a/test.txt b/test.txt\n"
				+ "index f55b5c9..c5ec8fd 100644\n" + "--- a/test.txt\n"
				+ "+++ b/test.txt\n" + "@@ -4,3 +4,3 @@\n" + " 3\n" + "-4\n"
				+ "+4a\n" + " 5\n";
		assertEquals(expected.toString(), actual);
