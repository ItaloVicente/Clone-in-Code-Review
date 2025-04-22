	@Test
	public void testTarWithSubdir() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b.c"
		writeTrashFile("b0c"
		writeTrashFile("c"
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("b.c").call();
		git.add().addFilepattern("b0c").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("populate toplevel").call();
		writeTrashFile("b/b"
		writeTrashFile("b/a"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("add subdir").call();

				"git archive --format=tar master"
		String[] expect = { "a"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

