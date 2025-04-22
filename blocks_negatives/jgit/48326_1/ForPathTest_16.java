		}

		assertEquals(a, TreeWalk.forPath(or, "a", tree).getObjectId(0));
		assertEquals(a, TreeWalk.forPath(or, "a/", tree).getObjectId(0));
		assertEquals(null, TreeWalk.forPath(or, "/a", tree));
		assertEquals(null, TreeWalk.forPath(or, "/a/", tree));

		assertEquals(aDotB.getObjectId(), TreeWalk.forPath(or, "a.b", tree)
				.getObjectId(0));
		assertEquals(null, TreeWalk.forPath(or, "/a.b", tree));
		assertEquals(null, TreeWalk.forPath(or, "/a.b/", tree));
		assertEquals(aDotB.getObjectId(), TreeWalk.forPath(or, "a.b/", tree)
				.getObjectId(0));

		assertEquals(aZeroB.getObjectId(), TreeWalk.forPath(or, "a0b", tree)
				.getObjectId(0));
