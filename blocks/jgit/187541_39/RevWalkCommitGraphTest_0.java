	@Test
	public void testMergedInto() throws Exception {
		RevCommit c1 = commit();
		Ref branch1 = branch(c1
		RevCommit c2 = commit(c1);
		Ref branch2 = branch(c2
		RevCommit c3 = commit(c2);
		Ref branch3 = branch(c3
		RevCommit c4 = commit(c1);
		Ref branch4 = branch(c4
		RevCommit c5 = commit(c4);
		Ref branch5 = branch(c5
		enableAndWriteCommitGraph();
		RevCommit c6 = commit(c1);
		Ref branch6 = branch(c6
		RevCommit c7 = commit(c2
		Ref branch7 = branch(c7
		RevCommit c8 = commit(c5);
		Ref branch8 = branch(c8
		RevCommit c9 = commit(c4
		Ref branch9 = branch(c9


		reinitializeRevWalk();
		assertFalse(isObjectIdInGraph(c9));
		assertRefsEquals(asList(branch9)

		assertFalse(isObjectIdInGraph(c8));
		assertRefsEquals(asList(branch8)

		assertFalse(isObjectIdInGraph(c7));
		assertRefsEquals(asList(branch7)

		assertFalse(isObjectIdInGraph(c6));
		assertRefsEquals(asList(branch6

		assertTrue(isObjectIdInGraph(c5));
		assertRefsEquals(asList(branch5

		assertTrue(isObjectIdInGraph(c4));
		assertRefsEquals(asList(branch4
				allMergedInto(c4));

		assertTrue(isObjectIdInGraph(c3));
		assertRefsEquals(asList(branch3)

		assertTrue(isObjectIdInGraph(c2));
		assertRefsEquals(asList(branch2

		assertTrue(isObjectIdInGraph(c1));
		assertRefsEquals(asList(branch1
				branch6
	}

	boolean isObjectIdInGraph(AnyObjectId id) {
		return rw.commitGraph().findGraphPosition(id) >= 0;
	}

	List<Ref> allMergedInto(RevCommit needle) throws IOException {
		List<Ref> refs = db.getRefDatabase().getRefs();
		return rw.getMergedInto(rw.lookupCommit(needle)
	}

	void assertRefsEquals(List<Ref> expecteds
		assertEquals(expecteds.size()
		Collections.sort(expecteds
		Collections.sort(actuals
		for (int i = 0; i < expecteds.size(); i++) {
			Ref expected = expecteds.get(i);
			Ref actual = actuals.get(i);
			assertEquals(expected.getName()
			assertEquals(expected.getObjectId()
		}
	}

