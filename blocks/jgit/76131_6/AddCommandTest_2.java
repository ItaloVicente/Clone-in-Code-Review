			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern("src/a.txt").call();
			git.commit().setMessage("c2").call();
			assertTrue(git.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("foobar\n"
			git.checkout().setName(c1.getName()).call();
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals(
					"foo\n"
		}
	}

	@Theory
	public void testBuiltinCleanFilter(boolean sleepBeforeAdd)
			throws IOException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.tmp"
		File script = writeTempFile("sed s/o/e/g");
		File f = writeTrashFile("src/a.txt"

		Repository.registerCommand(
				org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
						+ "lfs/smudge"
				null);

		try (Git git = new Git(db)) {
			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern(".gitattributes").call();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setBoolean("filter"
			config.save();

			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
					.addFilepattern(".gitattributes").call();

			assertEquals(
					"[.gitattributes
					indexState(CONTENT));

			RevCommit c1 = git.commit().setMessage("c1").call();
			assertTrue(git.status().call().isClean());
			f = writeTrashFile("src/a.txt"
			if (!sleepBeforeAdd) {
