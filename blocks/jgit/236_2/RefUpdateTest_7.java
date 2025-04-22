		Ref master = allRefs.get("refs/heads/master");
		Ref head = allRefs.get("HEAD");
		assertEquals("refs/heads/master"
		assertEquals("HEAD"
		assertTrue("is symbolic reference"
		assertSame(master
