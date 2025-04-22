	@Test
	public void testDuplicateParents() throws Exception {
		ObjectId commitId;
		RevCommit newCommit;
		final ObjectInserter ow = db.newObjectInserter();
		RevWalk rw = new RevWalk(db);
		ObjectId parentA = db.resolve("a");
		ObjectId parentB = db.resolve("b");
		ObjectId[] parentIds_AA = new ObjectId[] { parentA
		ObjectId[] parentIds_AB = new ObjectId[] { parentA
		ObjectId[] parentIds_BA = new ObjectId[] { parentB
		ObjectId[] parentIds_BBAB = new ObjectId[] { parentB
				parentB };

		try {
			commitId = commit(ow
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		commitId = commit(ow
		newCommit = rw.parseCommit(commitId);
		assertEquals(2

		commitId = commit(ow
		newCommit = rw.parseCommit(commitId);
		assertEquals(2

		try {
			commitId = commit(ow
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		commitId = commit(ow
		newCommit = rw.parseCommit(commitId);
		assertEquals(2

		commitId = commit(ow
		newCommit = rw.parseCommit(commitId);
		assertEquals(2

		try {
			commitId = commit(ow
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		try {
			commitId = commit(ow
					Arrays.asList(parentIds_AA));
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		commitId = commit(ow
		newCommit = rw.parseCommit(commitId);
		assertEquals(2

		commitId = commit(ow
		newCommit = rw.parseCommit(commitId);
		assertEquals(2

		try {
			commitId = commit(ow
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}
	}

