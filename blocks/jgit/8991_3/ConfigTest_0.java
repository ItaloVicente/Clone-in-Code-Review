		assertEquals("[s \"b\"]\n\tc = one-two\n"
	}

	@Test
	public void testGetFastForwardMergeoptions() throws ConfigInvalidException {
		assertSame(FastForwardMode.MergeOptions.__FF
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
				FastForwardMode.MergeOptions.__FF));
		c = parse("[branch \"side\"]\n\tmergeoptions = --ff-only\n");
		assertSame(FastForwardMode.MergeOptions.__FF_ONLY
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
				FastForwardMode.MergeOptions.__FF_ONLY));
		c = parse("[branch \"side\"]\n\tmergeoptions = --ff\n");
		assertSame(FastForwardMode.MergeOptions.__FF
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
				FastForwardMode.MergeOptions.__FF));
		c = parse("[branch \"side\"]\n\tmergeoptions = --no-ff\n");
		assertSame(FastForwardMode.MergeOptions.__NO_FF
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
				FastForwardMode.MergeOptions.__NO_FF));
	}

	@Test
	public void testSetFastForwardMergeoptions() {
		final Config c = new Config();
		c.setEnum("branch"
				FastForwardMode.MergeOptions.valueOf(FastForwardMode.FF));
		assertEquals("[branch \"side\"]\n\tmergeoptions = --ff\n"
		c.setEnum("branch"
				FastForwardMode.MergeOptions.valueOf(FastForwardMode.FF_ONLY));
		assertEquals("[branch \"side\"]\n\tmergeoptions = --ff-only\n"
				c.toText());
		c.setEnum("branch"
				FastForwardMode.MergeOptions.valueOf(FastForwardMode.NO_FF));
		assertEquals("[branch \"side\"]\n\tmergeoptions = --no-ff\n"
				c.toText());
	}

	@Test
	public void testGetFastForwardMerge() throws ConfigInvalidException {
		assertSame(FastForwardMode.Merge.TRUE
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		c = parse("[merge]\n\tff = only\n");
		assertSame(FastForwardMode.Merge.ONLY
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		c = parse("[merge]\n\tff = true\n");
		assertSame(FastForwardMode.Merge.TRUE
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		c = parse("[merge]\n\tff = false\n");
		assertSame(FastForwardMode.Merge.FALSE
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
	}

	@Test
	public void testSetFastForwardMerge() {
		final Config c = new Config();
		c.setEnum("merge"
		assertEquals("[merge]\n\tff = true\n"
		c.setEnum("merge"
				FastForwardMode.Merge.valueOf(FastForwardMode.FF_ONLY));
		assertEquals("[merge]\n\tff = only\n"
				c.toText());
		c.setEnum("merge"
				FastForwardMode.Merge.valueOf(FastForwardMode.NO_FF));
		assertEquals("[merge]\n\tff = false\n"
