		RefUpdate update = tr.getRepository().getRefDatabase().newUpdate(tempRef
		update.setForceUpdate(true);
		update.delete();

		bb.commit().message("B").add("B"

		FileBasedConfig config = repo.getConfig();
		config.setString(ConfigConstants.CONFIG_GC_SECTION
			ConfigConstants.CONFIG_KEY_PRUNEEXPIRE
		config.save();

		gc.setPackExpireAgeMillis(0);

		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(6
		assertEquals(1
		assertEquals(0
