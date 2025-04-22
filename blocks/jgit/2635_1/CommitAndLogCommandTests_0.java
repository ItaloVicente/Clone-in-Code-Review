
	@Test
	public void testOnlyOptionWithTrackedFile() throws IOException
			NoFilepatternException
			ConcurrentRefUpdateException
			WrongRepositoryStateException {
		final File fileA = new File(db.getWorkTree()
		FileUtils.createNewFile(fileA);
		PrintWriter writer = new PrintWriter(fileA);
		writer.print("contentA");
		writer.close();

		final File fileB = new File(db.getWorkTree()
		FileUtils.createNewFile(fileB);
		writer = new PrintWriter(fileB);
		writer.print("contentB");
		writer.close();

		final Git git = new Git(db);
		git.add().addFilepattern(".").call();

		assertEquals("[a.txt
				+ "[b.txt

		git.commit().setOnly("b.txt").setMessage("commit").call();

		final Iterator<RevCommit> commits = git.log().call().iterator();
		final TreeWalk walk = new TreeWalk(git.getRepository());
		walk.addTree(commits.next().getTree());

		assertFalse(commits.hasNext());

		assertTrue(walk.next());
		assertEquals("b.txt"

		assertFalse(walk.next());

		assertEquals("[a.txt
				indexState(CONTENT));
	}

	@Test
	@SuppressWarnings("null")
	public void testOnlyOptionWithUntrackedFile() throws IOException
			NoFilepatternException
			ConcurrentRefUpdateException
			WrongRepositoryStateException {
		final File fileA = new File(db.getWorkTree()
		FileUtils.createNewFile(fileA);
		PrintWriter writer = new PrintWriter(fileA);
		writer.print("contentA");
		writer.close();

		final Git git = new Git(db);
		git.add().addFilepattern(".").call();

		final File fileB = new File(db.getWorkTree()
		FileUtils.createNewFile(fileB);
		writer = new PrintWriter(fileB);
		writer.print("contentB");
		writer.close();

		assertEquals("[a.txt
				indexState(CONTENT));

		JGitInternalException exception = null;
		try {
			git.commit().setOnly("b.txt").setMessage("first commit").call();
		} catch (JGitInternalException e) {
			exception = e;
		}
		assertNotNull(exception);
		assertEquals(MessageFormat.format(JGitText.get().entryNotFoundByPath
				"b.txt")

		assertEquals("[a.txt
				indexState(CONTENT));
	}

	@Test
	public void testOnlyOptionWithModifiedFile() throws IOException
			NoFilepatternException
			ConcurrentRefUpdateException
			WrongRepositoryStateException {
		final File fileA = new File(db.getWorkTree()
		FileUtils.createNewFile(fileA);
		PrintWriter writer = new PrintWriter(fileA);
		writer.print("contentA");
		writer.close();

		final File fileB = new File(db.getWorkTree()
		FileUtils.createNewFile(fileB);
		writer = new PrintWriter(fileB);
		writer.print("contentB");
		writer.close();

		final Git git = new Git(db);
		git.add().addFilepattern(".").call();

		writer = new PrintWriter(fileB);
		writer.print("modified contentB");
		writer.close();

		assertEquals("[a.txt
				+ "[b.txt

		git.commit().setOnly("b.txt").setMessage("first commit").call();

		final Iterator<RevCommit> commits = git.log().call().iterator();
		final TreeWalk walk = new TreeWalk(git.getRepository());
		walk.addTree(commits.next().getTree());

		assertFalse(commits.hasNext());

		assertTrue(walk.next());
		assertEquals("b.txt"
		assertEquals("modified contentB"

		assertFalse(walk.next());

		assertEquals("[a.txt
				indexState(CONTENT));
	}
