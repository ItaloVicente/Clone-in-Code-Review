		Ref head = allRefs.get("HEAD");
		Ref newref = allRefs.get("refs/heads/newref");
		assertEquals("refs/heads/newref"
		assertEquals("HEAD"
		assertTrue("is symbolic reference"
		assertSame(newref
