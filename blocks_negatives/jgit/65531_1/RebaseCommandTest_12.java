		RevWalk rw = new RevWalk(db);
		rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
		assertDerivedFrom(rw.next(), e);
		assertDerivedFrom(rw.next(), d);
		assertDerivedFrom(rw.next(), c);
		assertEquals(b, rw.next());
		assertEquals(a, rw.next());
