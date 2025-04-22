	@Test
	public void testCleanFilter() throws IOException
			GitAPIException {
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("sed s/o/e/g");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
				.call();

		assertEquals(
				"[src/a.tmp
				indexState(CONTENT));
	}

	@Test
	public void testCleanFilterEnvironment()
			throws IOException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("echo $GIT_DIR; echo 1 >xyz");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();
		git.add().addFilepattern("src/a.txt").call();

		String gitDir = db.getDirectory().getAbsolutePath();
		assertEquals("[src/a.txt
				+ "\n]"
		assertTrue(new File(db.getWorkTree()
	}

	@Test
	public void testMultipleCleanFilter() throws IOException
		writeTrashFile(".gitattributes"
				"*.txt filter=tstFilter\n*.tmp filter=tstFilter2");
		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("sed s/o/e/g");
		File script2 = writeTempFile("sed s/f/x/g");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.setString("filter"
				"sh " + slashify(script2.getPath()));
		config.save();

		git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
				.call();

		assertEquals(
				"[src/a.tmp
				indexState(CONTENT));

	}

	@Test
	public void testCommandInjection() throws IOException
		writeTrashFile("; echo virus"
		File script = writeTempFile("sed s/o/e/g");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()) + " %f");
		writeTrashFile(".gitattributes"

		git.add().addFilepattern("; echo virus").call();
		assertEquals("[; echo virus
				indexState(CONTENT));
	}

	@Test
	public void testBadCleanFilter() throws IOException
		writeTrashFile("a.txt"
		File script = writeTempFile("sedfoo s/o/e/g");

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
		File script = writeTempFile("sed s/o/e/g");

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
				"sh " + slashify(script.getPath()));
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
	public void testNotApplicableFilter() throws IOException
		writeTrashFile("a.txt"
		File script = writeTempFile("sed s/o/e/g");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + script.getPath());
		config.save();
		writeTrashFile(".gitattributes"

		git.add().addFilepattern("a.txt").call();

		assertEquals("[a.txt
	}

	private File writeTempFile(String body) throws IOException {
		File f = File.createTempFile("AddCommandTest_"
		JGitTestUtil.write(f
		return f;
	}

