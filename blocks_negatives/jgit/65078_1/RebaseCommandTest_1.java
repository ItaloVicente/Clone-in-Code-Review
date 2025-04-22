		rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
		RevCommit newF = rw.next();
		assertDerivedFrom(newF, f);
		assertEquals(2, newF.getParentCount());
		RevCommit newD = rw.next();
		assertDerivedFrom(newD, d);
		if (testConflict)
			assertEquals("d new", readFile("conflict", newD));
		RevCommit newE = rw.next();
		assertDerivedFrom(newE, e);
		if (testConflict)
			assertEquals("e new", readFile("conflict", newE));
		assertEquals(newD, newF.getParent(0));
		assertEquals(newE, newF.getParent(1));
		assertDerivedFrom(rw.next(), c);
		assertEquals(b, rw.next());
		assertEquals(a, rw.next());
