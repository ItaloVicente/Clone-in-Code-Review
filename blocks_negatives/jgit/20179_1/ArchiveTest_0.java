				"git archive --format=zip master", db);
		String[] expect = { "a", "b.c", "b0c", "b/", "b/a", "b/b", "c" };
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect, actual);
	}

	@Test
	public void testTarWithSubdir() throws Exception {
		writeTrashFile("a", "a file with content!");
		writeTrashFile("b.c", "before subdir in git sort order");
		writeTrashFile("b0c", "after subdir in git sort order");
		writeTrashFile("c", "");
		git.add().addFilepattern("a").call();
		git.add().addFilepattern("b.c").call();
		git.add().addFilepattern("b0c").call();
		git.add().addFilepattern("c").call();
		git.commit().setMessage("populate toplevel").call();
		writeTrashFile("b/b", "file in subdirectory");
		writeTrashFile("b/a", "another file in subdirectory");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("add subdir").call();

				"git archive --format=tar master", db);
		String[] expect = { "a", "b.c", "b0c", "b/", "b/a", "b/b", "c" };
		String[] actual = listTarEntries(result);
