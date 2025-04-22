	@Test
	public void testContentMergeXtheirs() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n4\n"
					read(new File(db.getWorkTree()
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE)
					.setContentMergeStrategy(ContentMergeStrategy.THEIRS)
					.call();
			assertEquals(MergeStatus.MERGED

			assertEquals("1\na(side)\n3\n4\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nb(side)\n3\n4\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()

			assertNull(result.getConflicts());

			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testContentMergeXours() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n4\n"
					read(new File(db.getWorkTree()
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE)
					.setContentMergeStrategy(ContentMergeStrategy.OURS).call();
			assertEquals(MergeStatus.MERGED

			assertEquals("1\na(main)\n3\n4\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nb(side)\n3\n4\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()

			assertNull(result.getConflicts());

			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testBinaryContentMerge() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile(".gitattributes"
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING

			assertEquals("main"


			assertEquals(RepositoryState.MERGING
		}
	}

	@Test
	public void testBinaryContentMergeXtheirs() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile(".gitattributes"
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE)
					.setContentMergeStrategy(ContentMergeStrategy.THEIRS)
					.call();
			assertEquals(MergeStatus.MERGED

			assertEquals("side"

			assertNull(result.getConflicts());
			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testBinaryContentMergeXours() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile(".gitattributes"
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE)
					.setContentMergeStrategy(ContentMergeStrategy.OURS).call();
			assertEquals(MergeStatus.MERGED

			assertEquals("main"

			assertNull(result.getConflicts());
			assertEquals(RepositoryState.SAFE
		}
	}

