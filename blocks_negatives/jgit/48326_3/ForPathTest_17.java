		assertEquals(aSlashB.getObjectId(), TreeWalk.forPath(or, "a/b", tree)
				.getObjectId(0));
		assertEquals(aSlashB.getObjectId(), TreeWalk.forPath(or, "b", a)
				.getObjectId(0));

		assertEquals(aSlashC, TreeWalk.forPath(or, "a/c", tree).getObjectId(0));
		assertEquals(aSlashC, TreeWalk.forPath(or, "c", a).getObjectId(0));

		assertEquals(aSlashCSlashD.getObjectId(),
				TreeWalk.forPath(or, "a/c/d", tree).getObjectId(0));
		assertEquals(aSlashCSlashD.getObjectId(), TreeWalk
				.forPath(or, "c/d", a).getObjectId(0));

		or.release();
		oi.release();
