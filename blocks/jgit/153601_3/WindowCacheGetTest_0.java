		WindowCacheStats s = cache.getStats();
		assertEquals(6
		assertEquals(17346
		assertEquals(0
		assertEquals(90
		assertTrue(s.hitRatio() > 0.0 && s.hitRatio() < 1.0);
		assertEquals(6
		assertEquals(0
		assertEquals(0
		assertEquals(6
		assertEquals(6
		assertTrue(s.missRatio() > 0.0 && s.missRatio() < 1.0);
		assertEquals(96
		assertTrue(s.averageLoadTime() > 0.0);
		assertTrue(s.totalLoadTime() > 0.0);
