		try (RevWalk rw = new RevWalk(db)) {
			rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
			assertDerivedFrom(rw.next()
			assertDerivedFrom(rw.next()
			assertDerivedFrom(rw.next()
			assertEquals(b
			assertEquals(a
		}
