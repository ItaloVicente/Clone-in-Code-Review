	@Test
	public void testArchiveIncludesSubmoduleDirectory() throws Exception {
		writeTrashFile("a"
		writeTrashFile("c"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("initial commit").call();
		git.submoduleAdd().setURI("./.").setPath("b").call().close();
		git.commit().setMessage("add submodule").call();

				"git archive --format=zip master"
		String[] expect = { ".gitmodules"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testTarIncludesSubmoduleDirectory() throws Exception {
		writeTrashFile("a"
		writeTrashFile("c"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("initial commit").call();
		git.submoduleAdd().setURI("./.").setPath("b").call().close();
		git.commit().setMessage("add submodule").call();

				"git archive --format=tar master"
		String[] expect = { ".gitmodules"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

