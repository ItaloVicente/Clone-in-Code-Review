		WindowCacheStatsMXBean s = cache.getStats();
		assertEquals(6
		assertEquals(17346
		assertEquals(0
		assertEquals(90
		assertTrue(s.getHitRatio() > 0.0 && s.getHitRatio() < 1.0);
		assertEquals(6
		assertEquals(0
		assertEquals(0
		assertEquals(6
		assertEquals(6
		assertTrue(s.getMissRatio() > 0.0 && s.getMissRatio() < 1.0);
		assertEquals(96
		assertTrue(s.getAverageLoadTime() > 0.0);
		assertTrue(s.getTotalLoadTime() > 0.0);
