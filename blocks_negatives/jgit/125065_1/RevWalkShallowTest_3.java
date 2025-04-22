		rw = createRevWalk();
		a = rw.lookupCommit(a);
		b = rw.lookupCommit(b);
		c = rw.lookupCommit(c);
		d = rw.lookupCommit(d);

		rw.reset();
		markStart(d);
		assertCommit(d, rw.next());
		assertCommit(c, rw.next());
