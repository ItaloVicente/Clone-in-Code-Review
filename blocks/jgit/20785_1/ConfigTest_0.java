		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.NO_FF
	}

	@Test
	public void testCombinedMergeOptions() throws ConfigInvalidException {
		MergeConfig mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF
		assertTrue(mergeConfig.isCommit());
		assertFalse(mergeConfig.isSquash());
		c = parse("[merge]\n\tff = false\n"
				+ "[branch \"side\"]\n\tmergeoptions = --ff-only\n");
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		assertTrue(mergeConfig.isCommit());
		assertFalse(mergeConfig.isSquash());
		c = parse("[merge]\n\tff = only\n"
				+ "[branch \"side\"]\n\tmergeoptions = --squash\n");
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		assertTrue(mergeConfig.isCommit());
		assertTrue(mergeConfig.isSquash());
		c = parse("[merge]\n\tff = false\n"
				+ "[branch \"side\"]\n\tmergeoptions = --ff-only --no-commit\n");
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		assertFalse(mergeConfig.isCommit());
		assertFalse(mergeConfig.isSquash());
