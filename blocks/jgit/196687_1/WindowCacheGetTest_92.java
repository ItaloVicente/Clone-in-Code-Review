		assertTrue(0 < s.getAverageLoadTime()
				"average load time should be > 0");
		assertTrue(0 < s.getOpenByteCount()
		assertTrue(0 <= s.getEvictionCount()
		assertTrue(0 < s.getHitCount()
		assertTrue(0 < s.getHitRatio()
		assertTrue(1 > s.getHitRatio()
		assertTrue(0 < s.getLoadCount()
		assertTrue(0 <= s.getLoadFailureCount()
				"load failure count should be >= 0");
		assertTrue(0.0 <= s.getLoadFailureRatio()
				"load failure ratio should be >= 0");
		assertTrue(1 > s.getLoadFailureRatio()
				"load failure ratio should be < 1");
		assertTrue(0 < s.getLoadSuccessCount()
				"load success count should be > 0");
		assertTrue(s.getOpenByteCount() <= cfg.getPackedGitLimit()
				"open byte count should be <= core.packedGitLimit");
		assertTrue(s.getOpenFileCount() <= cfg.getPackedGitOpenFiles()
				"open file count should be <= core.packedGitOpenFiles");
		assertTrue(0 <= s.getMissCount()
		assertTrue(0 <= s.getMissRatio()
		assertTrue(1 > s.getMissRatio()
		assertTrue(0 < s.getRequestCount()
		assertTrue(0 < s.getTotalLoadTime()
