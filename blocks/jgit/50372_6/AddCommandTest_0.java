	@Test
	public void testCleanFilter() throws IOException
			GitAPIException {
		writeTrashFile("a.txt"
		writeTrashFile("a.tmp"
		File script = writeTempFile("sed s/o/e/ -");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes"

		git.add().addFilepattern("a.txt").addFilepattern("a.tmp").call();

		assertEquals(
				"[a.tmp
				indexState(CONTENT));
	}

	@Test
	public void testCommandInjection() throws IOException
		writeTrashFile("; echo virus"
		File script = writeTempFile("sed s/o/e/ -");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + script.getPath() + " %f");
		writeTrashFile(".gitattributes"

		git.add().addFilepattern("; echo virus").call();
		assertEquals("[; echo virus
				indexState(CONTENT));
	}

	@Test
	public void testBadCleanFilter() throws IOException
		writeTrashFile("a.txt"
		File script = writeTempFile("sedfoo s/o/e/ -");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes"

		try {
			git.add().addFilepattern("a.txt").call();
			fail("Didn't received the expected exception");
		} catch (FilterFailedException e) {
			assertEquals(127
		}
	}

	@Test
	public void testBadCleanFilter2() throws IOException
		writeTrashFile("a.txt"
		File script = writeTempFile("sed s/o/e/ -");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"shfoo " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes"

		try {
			git.add().addFilepattern("a.txt").call();
			fail("Didn't received the expected exception");
		} catch (FilterFailedException e) {
			assertEquals(127
		}
	}

	@Test
	public void testCleanFilterReturning12() throws IOException
			GitAPIException {
		writeTrashFile("a.txt"
		File script = writeTempFile("exit 12");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes"

		try {
			git.add().addFilepattern("a.txt").call();
			fail("Didn't received the expected exception");
		} catch (FilterFailedException e) {
			assertEquals(12
		}
	}

	@Test
	public void testSmudgeFilter() throws IOException
			GitAPIException {
		writeTrashFile("a.txt"
		File script = writeTempFile("sed s/o/e/ -");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes"

		git.add().addFilepattern("a.txt").call();

		assertEquals("[a.txt
				indexState(CONTENT));
	}

	private File writeTempFile(String body) throws IOException {
		File f = File.createTempFile("AddCommandTest_"
		JGitTestUtil.write(f
		return f;
	}

