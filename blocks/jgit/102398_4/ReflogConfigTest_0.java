	@SuppressWarnings("deprecation")
	@Test
	public void testlogAllRefUpdatesRespectsWhetherRepoIsBare()
			throws Exception {
		Repository bareDb = createBareRepository();

		db.getConfig().unset(CORE
		bareDb.getConfig().unset(CORE

		assertTrue(db.getConfig().get(CoreConfig.KEY).isLogAllRefUpdates());
		assertTrue(bareDb.getConfig().get(CoreConfig.KEY).isLogAllRefUpdates());

		assertTrue(db.getConfig().get(CoreConfig.key(db)).isLogAllRefUpdates());
		assertFalse(
				bareDb.getConfig().get(CoreConfig.key(bareDb)).isLogAllRefUpdates());

		db.getConfig().setBoolean(CORE
		bareDb.getConfig().setBoolean(CORE
		assertTrue(db.getConfig().get(CoreConfig.key(db)).isLogAllRefUpdates());
		assertTrue(
				bareDb.getConfig().get(CoreConfig.key(bareDb)).isLogAllRefUpdates());

		db.getConfig().setBoolean(CORE
		bareDb.getConfig().setBoolean(CORE
		assertFalse(db.getConfig().get(CoreConfig.key(db)).isLogAllRefUpdates());
		assertFalse(
				bareDb.getConfig().get(CoreConfig.key(bareDb)).isLogAllRefUpdates());
	}

	@Test
	public void testLogAllRefUpdatesInitialValueInConfigMatchesCGit()
			throws Exception {
		assertEquals("true"

		Repository bareDb = createBareRepository();
		assertNull(bareDb.getConfig().getString(CORE
	}

