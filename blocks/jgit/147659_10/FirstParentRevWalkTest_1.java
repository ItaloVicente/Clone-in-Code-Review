	@Test
	public void testUnparsedFirstParentOfFirstParentMarkedUninteresting()
			throws Exception {
		ObjectId a = unparsedCommit();
		ObjectId b1 = unparsedCommit(a);
		ObjectId b2 = unparsedCommit(a);
		ObjectId c1 = unparsedCommit(b1);
		ObjectId c2 = unparsedCommit(b2);
		ObjectId d = unparsedCommit(c1

		rw.reset();
		rw.setFirstParent(true);
		RevCommit parsedD = rw.parseCommit(d);
		markStart(parsedD);
		markUninteresting(rw.parseCommit(b1));
		assertCommit(parsedD
		assertCommit(rw.parseCommit(c1)
		assertNull(rw.next());
	}

