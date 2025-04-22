		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {
			String[] content1 = new String[] { "abc" };
			writeTrashFile("something_else.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("create file").call();

			String[] content2 = new String[] { "abc"
			writeTrashFile("something_else.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("update file").call();

			String[] content3 = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			String[] content4 = new String[] { "abc"
			writeTrashFile("something_else.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("update file").call();

			String[] content5 = new String[] { "abc" };
			writeTrashFile("something_else.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("create file").call();

			String[] content6 = new String[] { "third"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("create file").call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredC2 = new FilteredRevCommit(c2
					Arrays.asList(filteredC1));

			revWalk.parseHeaders(filteredC2);

			try (BlameGenerator generator = new BlameGenerator(db
					"file.txt")) {
				generator.push(filteredC2);
				assertEquals(3

				assertTrue(generator.next());
				assertEquals(c2
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals(0
				assertEquals(1
				assertEquals("file.txt"

				assertTrue(generator.next());
				assertEquals(c1
				assertEquals(2
				assertEquals(1
				assertEquals(3
				assertEquals(0
				assertEquals(2
				assertEquals("file.txt"

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testMergeSingleBlame() throws Exception {
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {

			String[] contentA = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			createBranch(c1
			checkoutBranch("refs/heads/side");
			String[] contentSide = new String[] { "first"
					"fourth" };
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit sideCommit = git.commit().setMessage("create file")
					.call();
			checkoutBranch("refs/heads/master");

			git.merge().setMessage("merge").include(sideCommit)
					.setStrategy(MergeStrategy.RESOLVE).call();

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit mergeCommit = it.next();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredSide = new FilteredRevCommit(sideCommit
					Arrays.asList(filteredC1));
			RevCommit filteredMerge = new FilteredRevCommit(
					mergeCommit

			revWalk.parseHeaders(filteredMerge);

			try (BlameGenerator generator = new BlameGenerator(db
					"file.txt")) {
				generator.push(filteredMerge);
				assertEquals(4

				assertTrue(generator.next());
				assertEquals(mergeCommit
				assertEquals(2
				assertEquals(2
				assertEquals(4
				assertEquals(2
				assertEquals(4
				assertEquals("file.txt"

				assertTrue(generator.next());
				assertEquals(filteredC1
				assertEquals(2
				assertEquals(0
				assertEquals(2
				assertEquals(0
				assertEquals(2
				assertEquals("file.txt"

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testMergeBlame() throws Exception {
		try (Git git = new Git(db);
				RevWalk revWalk = new RevWalk(git.getRepository())) {

			String[] contentA = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			createBranch(c1
			checkoutBranch("refs/heads/side");
			String[] contentSide = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit sideCommit = git.commit().setMessage("create file")
					.call();

			checkoutBranch("refs/heads/master");
			String[] contentB = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("create file").call();

			git.merge().setMessage("merge")
					.include(sideCommit).setStrategy(MergeStrategy.RESOLVE)
					.call();
			String[] contentMerge = new String[] { "first"
					"fourth" };
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit mergeCommit = git.commit().setMessage("merge commit")
					.call();

			RevCommit filteredC1 = new FilteredRevCommit(c1);
			RevCommit filteredSide = new FilteredRevCommit(sideCommit
					Arrays.asList(filteredC1));
			RevCommit filteredC2 = new FilteredRevCommit(c2
					Arrays.asList(filteredC1));

			RevCommit filteredMerge = new FilteredRevCommit(
					mergeCommit

			revWalk.parseHeaders(filteredMerge);

			try (BlameGenerator generator = new BlameGenerator(db
					"file.txt")) {
				generator.push(filteredMerge);
				assertEquals(4

				assertTrue(generator.next());
				assertEquals(filteredC2
				assertEquals(1
				assertEquals(3
				assertEquals(4
				assertEquals(2
				assertEquals(3
				assertEquals("file.txt"

				assertTrue(generator.next());
				assertEquals(filteredSide
				assertEquals(1
				assertEquals(2
				assertEquals(3
				assertEquals(2
				assertEquals(3
				assertEquals("file.txt"

				assertTrue(generator.next());
				assertEquals(filteredC1
				assertEquals(2
				assertEquals(0
				assertEquals(2
				assertEquals(0
				assertEquals(2
				assertEquals("file.txt"

				assertFalse(generator.next());
			}
		}
	}

	@Test
	public void testBoundLineDelete_walk() throws Exception {
