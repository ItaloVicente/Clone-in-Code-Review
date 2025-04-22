		RevCommit next;
		if (expectedPullMode == TestPullMode.MERGE) {
			next = rw.next();
			assertEquals(2, next.getParentCount());
			assertEquals(merge, next.getParent(0));
			assertEquals(sourceCommit, next.getParent(1));
		} else {
			if (expectedPullMode == TestPullMode.REBASE_PREASERVE) {
