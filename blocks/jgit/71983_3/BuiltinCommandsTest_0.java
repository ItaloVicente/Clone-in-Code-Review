
	@Test
	public void testBuiltinSmudgeFilter() throws IOException
		db.registerComand(builtinCommandName
				new TestCommandFactory('s'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
		config.save();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("Hello again"
		deleteTrashFile("Test.txt");
		git.checkout().addPath("Test.txt").call();
		assertEquals("sHseslslsos sasgsasisn"

		writeTrashFile("Test.bin"
		git.add().addFilepattern("Test.bin").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		deleteTrashFile("Test.bin");
		git.checkout().addPath("Test.bin").call();
		assertEquals("Hello again"

		config.setString("filter"
		config.save();

		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		config.setString("filter"
		config.save();
	}

	@Test
	public void testBuiltinCleanAndSmudgeFilter() throws IOException
		db.registerComand(builtinCommandPrefix + "smudge"
				new TestCommandFactory('s'));
		db.registerComand(builtinCommandPrefix + "clean"
				new TestCommandFactory('c'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
		config.setString("filter"
				builtinCommandPrefix + "clean");
		config.save();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("Hello again"
		deleteTrashFile("Test.txt");
		git.checkout().addPath("Test.txt").call();
		assertEquals("scsHscsescslscslscsoscs scsascsgscsascsiscsn"
				read("Test.txt"));

		writeTrashFile("Test.bin"
		git.add().addFilepattern("Test.bin").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		deleteTrashFile("Test.bin");
		git.checkout().addPath("Test.bin").call();
		assertEquals("Hello again"

		config.setString("filter"
		config.save();

		git.add().addFilepattern("Test.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		config.setString("filter"
		config.save();
	}

