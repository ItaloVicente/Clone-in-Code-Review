
	@Test
	public void testSmudgeFilter_modifyExisting() throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content1 = git.commit().setMessage("add content").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content2 = git.commit().setMessage("changed content").call();

		git.checkout().setName(content1.getName()).call();
		git.checkout().setName(content2.getName()).call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals(Sets.of("src/a.txt")
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	public void testSmudgeFilter_createNew()
			throws IOException
		File script = writeTempFile("sed s/o/e/g");
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(script.getPath()));
		config.save();

		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		RevCommit initial = git.commit().setMessage("initial").call();

		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add filter").call();

		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		git.add().addFilepattern("src/a.tmp").addFilepattern("src/a.txt")
				.call();
		RevCommit content = git.commit().setMessage("added content").call();

		git.checkout().setName(initial.getName()).call();
		git.checkout().setName(content.getName()).call();

		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("foo"
		assertEquals("fee\n"
	}

	@Test
	@Ignore
	public void testSmudgeAndClean() throws IOException
		File clean_filter = writeTempFile("sed s/V1/@version/g -");
		File smudge_filter = writeTempFile("sed s/@version/V1/g -");

		Git git = new Git(db);
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				"sh " + slashify(smudge_filter.getPath()));
		config.setString("filter"
				"sh " + slashify(clean_filter.getPath()));
		config.save();
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".gitattributes").call();
		git.commit().setMessage("add attributes").call();

		writeTrashFile("filterTest.txt"
		git.add().addFilepattern("filterTest.txt").call();
		git.commit().setMessage("add filterText.txt").call();
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		git.checkout().setCreateBranch(true).setName("test2").call();
		writeTrashFile("filterTest.txt"
		git.add().addFilepattern("filterTest.txt").call();
		git.commit().setMessage("modified filterText.txt").call();

		assertTrue(git.status().call().isClean());
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));

		git.checkout().setName("refs/heads/test").call();
		assertTrue(git.status().call().isClean());
		assertEquals(
				"[.gitattributes
				indexState(CONTENT));
		assertEquals("hello world
	}

	private File writeTempFile(String body) throws IOException {
		File f = File.createTempFile("AddCommandTest_"
		JGitTestUtil.write(f
		return f;
	}
