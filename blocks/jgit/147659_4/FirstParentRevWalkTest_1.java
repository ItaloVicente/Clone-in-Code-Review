	@Test
	public void testUninterestingCommitWithTwoParents() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		RevCommit c1 = commit(b);
		RevCommit c2 = commit(b);
		RevCommit d = commit(c1

		RevCommit uA = commit(a
		RevCommit uB1 = commit(uA
		RevCommit uB2 = commit(uA
		RevCommit uninteresting = commit(uB1

		rw.reset();
		rw.setFirstParent(true);
		markStart(d);
		markUninteresting(uninteresting);

		assertCommit(d
		assertNull(rw.next());
	}

	@Test
	public void testUnparsedUninterestingCommitWithTwoParents()
			throws Exception {
		ObjectId a = unparsedCommit();
		ObjectId b = unparsedCommit(a);
		ObjectId c1 = unparsedCommit(b);
		ObjectId c2 = unparsedCommit(b);
		ObjectId d = unparsedCommit(c1

		ObjectId uA = unparsedCommit(a
		ObjectId uB1 = unparsedCommit(uA
		ObjectId uB2 = unparsedCommit(uA
		ObjectId uninteresting = unparsedCommit(uB1

		rw.reset();
		rw.setFirstParent(true);
		RevCommit parsedD = rw.parseCommit(d);
		markStart(parsedD);
		markUninteresting(rw.parseCommit(uninteresting));

		assertCommit(parsedD
		assertNull(rw.next());
	}

