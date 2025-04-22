	private void propagateUninteresting(int stoppingPoint)
			throws MissingObjectException
				   IOException {
		for (int overScan = OVER_SCAN; overScan > 0; overScan--) {
			DepthWalk.Commit c = (DepthWalk.Commit) uninteresting.next();
			if (c == null) {
				return;
			}
			if ((c.flags & UNINTERESTING) == 0) {
				throw new IllegalStateException("found interesting commit in uninteresting walk");
			}
			for (RevCommit p : c.parents) {
				DepthWalk.Commit dp = (DepthWalk.Commit) p;

				if ((p.flags & SEEN) != 0) {
					continue;
				}
				if ((p.flags & PARSED) == 0) {
					p.parseHeaders(walk);
				}
				p.flags |= SEEN;
				uninteresting.add(p);
			}
			walk.carryFlagsImpl(c);

			if (c.commitTime >= stoppingPoint) {
				overScan = OVER_SCAN + 1;
			}
		}
	}

