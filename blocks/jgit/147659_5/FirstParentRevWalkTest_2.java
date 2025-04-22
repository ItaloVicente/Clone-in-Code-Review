	@Test
	public void testUnparsedFirstParentMarkedUninteresting() throws Exception {
		ObjectId a = unparsedCommit();
		ObjectId b1 = unparsedCommit(a);
		ObjectId b2 = unparsedCommit(a);
		ObjectId c = unparsedCommit(b1

		rw.reset();
		rw.setFirstParent(true);
		RevCommit parsedC = rw.parseCommit(c);
		markStart(parsedC);
		markUninteresting(rw.parseCommit(b1));
		assertCommit(parsedC
		assertNull(rw.next());
	}

	@Test
	public void testUninterestingCommitWithTwoParents() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		RevCommit c1 = commit(b);
		RevCommit c2 = commit(b);
		RevCommit d = commit(c1);
		RevCommit e = commit(c1

		RevCommit uA = commit(a
		RevCommit uB1 = commit(uA
		RevCommit uB2 = commit(uA
		RevCommit uninteresting = commit(uB1

		rw.reset();
		rw.setFirstParent(true);
		markStart(e);
		markUninteresting(uninteresting);

		assertCommit(e
		assertNull(rw.next());
	}

	@Test
	public void testUnparsedUninterestingCommitWithTwoParents()
			throws Exception {
		ObjectId a = unparsedCommit();
		ObjectId b = unparsedCommit(a);
		ObjectId c1 = unparsedCommit(b);
		ObjectId c2 = unparsedCommit(b);
		ObjectId d = unparsedCommit(c1);
		ObjectId e = unparsedCommit(c1

		ObjectId uA = unparsedCommit(a
		ObjectId uB1 = unparsedCommit(uA
		ObjectId uB2 = unparsedCommit(uA
		ObjectId uninteresting = unparsedCommit(uB1

		rw.reset();
		rw.setFirstParent(true);
		RevCommit parsedE = rw.parseCommit(e);
		markStart(parsedE);
		markUninteresting(rw.parseCommit(uninteresting));

		assertCommit(parsedE
		assertNull(rw.next());
	}

