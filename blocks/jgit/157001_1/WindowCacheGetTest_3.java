		assertTrue("average load time should be > 0"
				0 < s.getAverageLoadTime());
		assertTrue("open byte count should be > 0"
		assertTrue("eviction count should be >= 0"
		assertTrue("hit count should be > 0"
		assertTrue("hit ratio should be > 0"
		assertTrue("hit ratio should be < 1"
		assertTrue("load count should be > 0"
		assertTrue("load failure count should be >= 0"
				0 <= s.getLoadFailureCount());
		assertTrue("load failure ratio should be >= 0"
				0.0 <= s.getLoadFailureRatio());
		assertTrue("load failure ratio should be < 1"
				1 > s.getLoadFailureRatio());
		assertTrue("load success count should be > 0"
				0 < s.getLoadSuccessCount());
		assertTrue("open byte count should be <= core.packedGitLimit"
				s.getOpenByteCount() <= cfg.getPackedGitLimit());
		assertTrue("open file count should be <= core.packedGitOpenFiles"
				s.getOpenFileCount() <= cfg.getPackedGitOpenFiles());
		assertTrue("miss success count should be >= 0"
		assertTrue("miss ratio should be > 0"
		assertTrue("miss ratio should be < 1"
		assertTrue("request count should be > 0"
		assertTrue("total load time should be > 0"
