
	@Test
	public void testExtend() {
		DirCacheEntry e = new DirCacheEntry("a"
		assertFalse(e.isExtended());
		e.setSkipWorkTree(true);
		assertTrue(e.isExtended());
	}

