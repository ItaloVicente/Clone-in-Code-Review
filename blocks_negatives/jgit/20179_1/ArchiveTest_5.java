				"git archive --format=zip master", db);
		String[] expect = { ".gitmodules", "a", "b/", "c" };
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect, actual);
	}

	@Test
	public void testTarIncludesSubmoduleDirectory() throws Exception {
		writeTrashFile("a", "a file with content!");
		writeTrashFile("c", "after submodule");
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("initial commit").call();
		git.submoduleAdd().setURI("./.").setPath("b").call().close();
		git.commit().setMessage("add submodule").call();

				"git archive --format=tar master", db);
		String[] expect = { ".gitmodules", "a", "b/", "c" };
		String[] actual = listTarEntries(result);
