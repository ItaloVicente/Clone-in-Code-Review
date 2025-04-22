	@Test
	public void testDuplicateParents() throws Exception {
		ObjectId commitId;
		RevCommit newCommit;
		final ObjectInserter ow = db.newObjectInserter();
		RevWalk rw = new RevWalk(db);
		ObjectId parentA = db.resolve("a");
		ObjectId parentB = db.resolve("b");
		ObjectId[] parentIds_AA = new ObjectId[] { parentA, parentA };
		ObjectId[] parentIds_AB = new ObjectId[] { parentA, parentB };
		ObjectId[] parentIds_BA = new ObjectId[] { parentB, parentA };
		ObjectId[] parentIds_BBAB = new ObjectId[] { parentB, parentB, parentA,
				parentB };

		try {
			commitId = commit(ow, db.readDirCache(), parentA, parentA);
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		commitId = commit(ow, db.readDirCache(), parentA, parentB);
		newCommit = rw.parseCommit(commitId);
		assertEquals(2, newCommit.getParentCount());

		commitId = commit(ow, db.readDirCache(), parentB, parentA);
		newCommit = rw.parseCommit(commitId);
		assertEquals(2, newCommit.getParentCount());

		try {
			commitId = commit(ow, db.readDirCache(), parentIds_AA);
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		commitId = commit(ow, db.readDirCache(), parentIds_AB);
		newCommit = rw.parseCommit(commitId);
		assertEquals(2, newCommit.getParentCount());

		commitId = commit(ow, db.readDirCache(), parentIds_BA);
		newCommit = rw.parseCommit(commitId);
		assertEquals(2, newCommit.getParentCount());

		try {
			commitId = commit(ow, db.readDirCache(), parentIds_BBAB);
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		try {
			commitId = commit(ow, db.readDirCache(),
					Arrays.asList(parentIds_AA));
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}

		commitId = commit(ow, db.readDirCache(), parentIds_AB);
		newCommit = rw.parseCommit(commitId);
		assertEquals(2, newCommit.getParentCount());

		commitId = commit(ow, db.readDirCache(), parentIds_BA);
		newCommit = rw.parseCommit(commitId);
		assertEquals(2, newCommit.getParentCount());

		try {
			commitId = commit(ow, db.readDirCache(), parentIds_BBAB);
			fail("an expected exception did not occur");
		} catch (IllegalArgumentException e) {
		}
	}

