		assertEquals("[s \"b\"]\n\tc = one-two\n", c.toText());
	}

	@Test
	public void testGetFastForwardMergeoptions() throws ConfigInvalidException {
		assertSame(FastForwardMode.MergeOptions.__FF, c.getEnum(
				ConfigConstants.CONFIG_BRANCH_SECTION, "side",
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS,
				FastForwardMode.MergeOptions.__FF));
		c = parse("[branch \"side\"]\n\tmergeoptions = --ff-only\n");
		assertSame(FastForwardMode.MergeOptions.__FF_ONLY, c.getEnum(
				ConfigConstants.CONFIG_BRANCH_SECTION, "side",
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS,
				FastForwardMode.MergeOptions.__FF_ONLY));
		c = parse("[branch \"side\"]\n\tmergeoptions = --ff\n");
		assertSame(FastForwardMode.MergeOptions.__FF, c.getEnum(
				ConfigConstants.CONFIG_BRANCH_SECTION, "side",
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS,
				FastForwardMode.MergeOptions.__FF));
		c = parse("[branch \"side\"]\n\tmergeoptions = --no-ff\n");
		assertSame(FastForwardMode.MergeOptions.__NO_FF, c.getEnum(
				ConfigConstants.CONFIG_BRANCH_SECTION, "side",
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS,
				FastForwardMode.MergeOptions.__NO_FF));
	}

	@Test
	public void testSetFastForwardMergeoptions() {
		final Config c = new Config();
		c.setEnum("branch", "side", "mergeoptions",
				FastForwardMode.MergeOptions.valueOf(FastForwardMode.FF));
		assertEquals("[branch \"side\"]\n\tmergeoptions = --ff\n", c.toText());
		c.setEnum("branch", "side", "mergeoptions",
				FastForwardMode.MergeOptions.valueOf(FastForwardMode.FF_ONLY));
		assertEquals("[branch \"side\"]\n\tmergeoptions = --ff-only\n",
				c.toText());
		c.setEnum("branch", "side", "mergeoptions",
				FastForwardMode.MergeOptions.valueOf(FastForwardMode.NO_FF));
		assertEquals("[branch \"side\"]\n\tmergeoptions = --no-ff\n",
				c.toText());
	}

	@Test
	public void testGetFastForwardMerge() throws ConfigInvalidException {
		assertSame(FastForwardMode.Merge.TRUE, c.getEnum(
				ConfigConstants.CONFIG_KEY_MERGE, null,
				ConfigConstants.CONFIG_KEY_FF, FastForwardMode.Merge.TRUE));
		c = parse("[merge]\n\tff = only\n");
		assertSame(FastForwardMode.Merge.ONLY, c.getEnum(
				ConfigConstants.CONFIG_KEY_MERGE, null,
				ConfigConstants.CONFIG_KEY_FF, FastForwardMode.Merge.ONLY));
		c = parse("[merge]\n\tff = true\n");
		assertSame(FastForwardMode.Merge.TRUE, c.getEnum(
				ConfigConstants.CONFIG_KEY_MERGE, null,
				ConfigConstants.CONFIG_KEY_FF, FastForwardMode.Merge.TRUE));
		c = parse("[merge]\n\tff = false\n");
		assertSame(FastForwardMode.Merge.FALSE, c.getEnum(
				ConfigConstants.CONFIG_KEY_MERGE, null,
				ConfigConstants.CONFIG_KEY_FF, FastForwardMode.Merge.FALSE));
	}

	@Test
	public void testSetFastForwardMerge() {
		final Config c = new Config();
		c.setEnum("merge", null, "ff", FastForwardMode.Merge.valueOf(FastForwardMode.FF));
		assertEquals("[merge]\n\tff = true\n", c.toText());
		c.setEnum("merge", null, "ff",
				FastForwardMode.Merge.valueOf(FastForwardMode.FF_ONLY));
		assertEquals("[merge]\n\tff = only\n",
				c.toText());
		c.setEnum("merge", null, "ff",
				FastForwardMode.Merge.valueOf(FastForwardMode.NO_FF));
		assertEquals("[merge]\n\tff = false\n", c.toText());
