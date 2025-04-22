
	@Test
	public void testExtend() {
		DirCacheEntry e = new DirCacheEntry("extended"
		assertFalse(e.isExtended());
		e.setSkipWorkTree(true);
		assertTrue(e.isExtended());

		e = new DirCacheEntry("not-extended"
		assertFalse(e.isExtended());
		e.setSkipWorkTree(false);
		assertFalse(e.isExtended());

		e = new DirCacheEntry("extended-toggle"
		assertFalse(e.isExtended());
		e.setSkipWorkTree(true);
		assertTrue(e.isExtended());
		e.setSkipWorkTree(false);
		assertTrue(e.isExtended());
	}

