				.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION,
						null, ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES,
						true);
		if (enableProtocolV2) {
			src.getRepository().getConfig().setInt("protocol", null, "version", 2);
		}
