
		cfg.setEnum("core"
				CoreConfig.LogRefUpdates.ALWAYS);
		cfg.save();
		assertEquals(CoreConfig.LogRefUpdates.ALWAYS
				cfg.getEnum(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES
						CoreConfig.LogRefUpdates.FALSE));

		commit("A Commit\n"
		assertTrue("Reflog for HEAD should contain three entries"
				db.getReflogReader(Constants.HEAD).getReverseEntries()
						.size() == 3);
