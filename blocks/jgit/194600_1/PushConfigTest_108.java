
	@Test
	public void pushDefaultMatch() throws Exception {
		assertTrue(PushDefault.NOTHING.matchConfigValue("nothing"));
		assertTrue(PushDefault.NOTHING.matchConfigValue("NOTHING"));
		assertTrue(PushDefault.CURRENT.matchConfigValue("current"));
		assertTrue(PushDefault.CURRENT.matchConfigValue("CURRENT"));
		assertTrue(PushDefault.UPSTREAM.matchConfigValue("upstream"));
		assertTrue(PushDefault.UPSTREAM.matchConfigValue("UPSTREAM"));
		assertTrue(PushDefault.UPSTREAM.matchConfigValue("tracking"));
		assertTrue(PushDefault.UPSTREAM.matchConfigValue("TRACKING"));
		assertTrue(PushDefault.SIMPLE.matchConfigValue("simple"));
		assertTrue(PushDefault.SIMPLE.matchConfigValue("SIMPLE"));
		assertTrue(PushDefault.MATCHING.matchConfigValue("matching"));
		assertTrue(PushDefault.MATCHING.matchConfigValue("MATCHING"));
	}

	@Test
	public void pushDefaultNoMatch() throws Exception {
		assertFalse(PushDefault.NOTHING.matchConfigValue("n"));
		assertFalse(PushDefault.CURRENT.matchConfigValue(""));
		assertFalse(PushDefault.UPSTREAM.matchConfigValue("track"));
	}

	@Test
	public void pushDefaultToConfigValue() throws Exception {
		assertEquals("nothing"
		assertEquals("current"
		assertEquals("upstream"
		assertEquals("simple"
		assertEquals("matching"
	}

	@Test
	public void testEmptyConfig() throws Exception {
		PushConfig cfg = parse("");
		assertEquals(PushRecurseSubmodulesMode.NO
		assertEquals(PushDefault.SIMPLE
	}

	@Test
	public void testConfig() throws Exception {
		PushConfig cfg = parse(
				"[push]\n\tdefault = tracking\n\trecurseSubmodules = on-demand\n");
		assertEquals(PushRecurseSubmodulesMode.ON_DEMAND
				cfg.getRecurseSubmodules());
		assertEquals(PushDefault.UPSTREAM
	}

	private static PushConfig parse(String content) throws Exception {
		Config c = new Config();
		c.fromText(content);
		return c.get(PushConfig::new);
	}

