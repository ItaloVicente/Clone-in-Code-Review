	private boolean searchPacksAgain(PackList old) {
		boolean trustFolderStat = config.getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION,
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT, true);

		return ((!trustFolderStat) || old.snapshot.isModified(packDirectory))
				&& old != scanPacks(old);
	}

