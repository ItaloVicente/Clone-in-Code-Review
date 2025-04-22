		assertEquals(Status.OK, res.getStatus());

		assertEquals("merge resolution", read(FILE1));
		assertEquals("new content two", read("file2"));
		assertEquals("more changess", read("file3"));
		assertEquals("fileg", read("fileg"));

		rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
		RevCommit newF = rw.next();
		assertDerivedFrom(newF, f);
		RevCommit newD = rw.next();
		assertDerivedFrom(newD, d);
		assertEquals(2, newD.getParentCount());
		RevCommit newC = rw.next();
		assertDerivedFrom(newC, c);
		RevCommit newE = rw.next();
		assertEquals(e, newE);
		assertEquals(newC, newD.getParent(0));
		assertEquals(e, newD.getParent(1));
		assertEquals(g, rw.next());
		assertEquals(b, rw.next());
		assertEquals(a, rw.next());
