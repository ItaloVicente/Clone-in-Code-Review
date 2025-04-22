
	@Test
	public void testExtend() {
		DirCacheEntry e = new DirCacheEntry("extended"
		assertFalse(e.isExtended());
		e.setSkipWorkTree(true);
		assertTrue(e.isExtended());

		e = new DirCacheEntry("notextended"
		assertFalse(e.isExtended());
		e.setSkipWorkTree(false);
		assertFalse(e.isExtended());
	}

