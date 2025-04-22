		try (RevWalk rw = new RevWalk(db)) {
			rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
			RevCommit newF = rw.next();
			assertDerivedFrom(newF
			assertEquals(2
			RevCommit newD = rw.next();
			assertDerivedFrom(newD
			if (testConflict)
				assertEquals("d new"
			RevCommit newE = rw.next();
			assertDerivedFrom(newE
			if (testConflict)
				assertEquals("e new"
			assertEquals(newD
			assertEquals(newE
			assertDerivedFrom(rw.next()
			assertEquals(b
			assertEquals(a
		}
