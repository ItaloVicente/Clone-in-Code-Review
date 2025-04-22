	@Test
	public void testSmudgeFilter_deleteFileAndRestoreFromCommit()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content = git.commit().setMessage("added content").call();

		deleteTrashFile("src/a.txt");
		git.checkout().setStartPoint(content.getName()).addPath("src/a.txt")
				.call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeFilter_deleteFileAndRestoreFromIndex()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		git.commit().setMessage("added content").call();

		deleteTrashFile("src/a.txt");
		git.checkout().addPath("src/a.txt").call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeFilter_deleteFileAndCreateBranchAndRestoreFromCommit()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content = git.commit().setMessage("added content").call();

		deleteTrashFile("src/a.txt");
		git.checkout().setName("newBranch").setCreateBranch(true)
				.setStartPoint(content).addPath("src/a.txt").call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

