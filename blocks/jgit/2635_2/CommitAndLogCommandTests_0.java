
	public void testOnlyOption() throws Exception {
		writeTrashFile("b.txt"
		final File c = writeTrashFile("c.txt"
		writeTrashFile("d.txt"
		writeTrashFile("f.txt"
		final File g = writeTrashFile("g.txt"
		writeTrashFile("h.txt"

		final Git git = new Git(db);

		git.add().addFilepattern(".").call();

		git.commit().setMessage("first commit").call();

		writeTrashFile("a.txt"
		writeTrashFile("e.txt"
		git.add().addFilepattern("a.txt").addFilepattern("e.txt").call();

		git.rm().addFilepattern("b.txt").addFilepattern("f.txt").call();

		write(c
		write(g
		git.add().addFilepattern("c.txt").addFilepattern("g.txt").call();


		final String indexState = indexState(CONTENT);
		assertEquals("[a.txt
				+ "[c.txt
				+ "[d.txt
				+ "[e.txt
				+ "[g.txt
				+ "[h.txt

		RevCommit commit = git.commit().setOnly("d.txt").setOnly("c.txt")
				.setOnly("b.txt").setOnly("a.txt").setMessage("second commit")
				.call();

		checkCommit(git.getRepository()
				"f.txt"

		assertEquals(indexState

		commit = git.commit().setMessage("third commit").call();

		checkCommit(git.getRepository()
				"e.txt"

		assertEquals(indexState
	}

	private void checkCommit(final Repository repo
			final String... files) throws Exception {
		TreeWalk walk = new TreeWalk(repo);
		walk.addTree(commit.getTree());
		for (final String f : files) {
			assertTrue(walk.next());
			assertEquals(f
		}
		assertFalse(walk.next());
	}

	@Test
	@SuppressWarnings("null")
	public void testOnlyOptionWithUntrackedFile() throws Exception {
		writeTrashFile("a.txt"

		final Git git = new Git(db);

		git.add().addFilepattern("a.txt").call();

		writeTrashFile("b.txt"

		JGitInternalException error = null;
		try {
			git.commit().setOnly("b.txt").setMessage("first commit").call();
		} catch (JGitInternalException e) {
			error = e;
		}
		assertNotNull(error);
		assertTrue(error.getMessage().contains("b.txt"));
	}

	@Test
	@SuppressWarnings("null")
	public void testOnlyOptionWithNotExistingFile() throws Exception {
		writeTrashFile("a.txt"

		final Git git = new Git(db);

		git.add().addFilepattern(".").call();

		JGitInternalException error = null;
		try {
			git.commit().setOnly("b.txt").setMessage("first commit").call();
		} catch (JGitInternalException e) {
			error = e;
		}
		assertNotNull(error);
		assertTrue(error.getMessage().contains("b.txt"));
	}
