		WindowCacheStats s = cache.getStats();
		assertEquals(6
		assertEquals(17346
		assertEquals(0
		assertEquals(90
		assertTrue(s.hitRate() > 0.0 && s.hitRate() < 1.0);
		assertEquals(6
		assertEquals(0
		assertEquals(0
		assertEquals(6
		assertEquals(6
		assertTrue(s.missRate() > 0.0 && s.missRate() < 1.0);
		assertEquals(96
		assertTrue(s.averageLoadTime() > 0.0);
		assertTrue(s.totalLoadTime() > 0.0);
