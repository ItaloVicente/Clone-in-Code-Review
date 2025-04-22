		assertTrue("average load time should be > 0",
				0 < s.getAverageLoadTime());
		assertTrue("open byte count should be > 0", 0 < s.getOpenByteCount());
		assertTrue("eviction count should be >= 0", 0 <= s.getEvictionCount());
		assertTrue("hit count should be > 0", 0 < s.getHitCount());
		assertTrue("hit ratio should be > 0", 0 < s.getHitRatio());
		assertTrue("hit ratio should be < 1", 1 > s.getHitRatio());
		assertTrue("load count should be > 0", 0 < s.getLoadCount());
		assertTrue("load failure count should be >= 0",
				0 <= s.getLoadFailureCount());
		assertTrue("load failure ratio should be >= 0",
				0.0 <= s.getLoadFailureRatio());
		assertTrue("load failure ratio should be < 1",
				1 > s.getLoadFailureRatio());
		assertTrue("load success count should be > 0",
				0 < s.getLoadSuccessCount());
		assertTrue("open byte count should be <= core.packedGitLimit",
				s.getOpenByteCount() <= cfg.getPackedGitLimit());
		assertTrue("open file count should be <= core.packedGitOpenFiles",
				s.getOpenFileCount() <= cfg.getPackedGitOpenFiles());
		assertTrue("miss success count should be >= 0", 0 <= s.getMissCount());
		assertTrue("miss ratio should be > 0", 0 <= s.getMissRatio());
		assertTrue("miss ratio should be < 1", 1 > s.getMissRatio());
		assertTrue("request count should be > 0", 0 < s.getRequestCount());
		assertTrue("total load time should be > 0", 0 < s.getTotalLoadTime());
