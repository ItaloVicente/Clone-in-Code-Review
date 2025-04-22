
		db.getConfig().setString(CORE
		bareDb.getConfig().setString(CORE
		assertEquals(LogAllRefUpdates.ALWAYS
				db.getConfig().get(CoreConfig.key(db)).getLogAllRefUpdates());
		assertTrue(db.getConfig().get(CoreConfig.key(db)).isLogAllRefUpdates());
		assertEquals(LogAllRefUpdates.ALWAYS
				bareDb.getConfig().get(CoreConfig.key(bareDb)).getLogAllRefUpdates());
		assertTrue(
				bareDb.getConfig().get(CoreConfig.key(bareDb)).isLogAllRefUpdates());
