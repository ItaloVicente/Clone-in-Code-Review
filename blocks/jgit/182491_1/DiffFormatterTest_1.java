	@Test
	public void testFilter() throws Exception {
		RevCommit parent;
		RevCommit head;
		try (Git git = new Git(db)) {
			writeTrashFile("foo.txt"
			writeTrashFile("src/some.txt"
			writeTrashFile("src/image.png"
			writeTrashFile("src/test.pdf"
			writeTrashFile("src/xyz.txt"
			git.add().addFilepattern(".").call();
			parent = git.commit().setMessage("initial").call();
			writeTrashFile("foo.txt"
			writeTrashFile("src/some.txt"
			writeTrashFile("src/image.png"
			writeTrashFile("src/test.pdf"
			writeTrashFile("src/xyz.txt"
			git.add().addFilepattern(".").call();
			head = git.commit().setMessage("second").call();
		}
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(os)) {
			dfmt.setRepository(db);
			List<TreeFilter> skip = new ArrayList<>();
			skip.add(PathSuffixFilter.create(".png"));
			skip.add(PathSuffixFilter.create(".pdf"));
			dfmt.setPathFilter(OrTreeFilter.create(skip).negate());
			dfmt.format(
					new CanonicalTreeParser(null
							parent.getTree())
					new CanonicalTreeParser(null
							head.getTree()));
			dfmt.flush();

			String actual = os.toString("UTF-8");

			String expected = "diff --git a/foo.txt b/foo.txt\n"
					+ "index 257cc56..b7d6715 100644\n"
					+ "--- a/foo.txt\n"
					+ "+++ b/foo.txt\n"
					+ "@@ -1 +1 @@\n"
					+ "-foo\n"
					+ "+FOO\n"
					+ "diff --git a/src/some.txt b/src/some.txt\n"
					+ "index 363ef61..76cea5f 100644\n"
					+ "--- a/src/some.txt\n"
					+ "+++ b/src/some.txt\n"
					+ "@@ -1 +1 @@\n"
					+ "-some\n"
					+ "+SOME\n"
					+ "diff --git a/src/xyz.txt b/src/xyz.txt\n"
					+ "index cd470e6..d4e3ab0 100644\n"
					+ "--- a/src/xyz.txt\n"
					+ "+++ b/src/xyz.txt\n"
					+ "@@ -1 +1 @@\n"
					+ "-xyz\n"
					+ "+XYZ\n";

			assertEquals(expected
		}
	}

