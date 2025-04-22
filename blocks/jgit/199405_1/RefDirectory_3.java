		trustFolderStat = db.getConfig()
				.getBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
		trustPackedRefsStat = db.getConfig()
				.getEnum(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_TRUST_PACKED_REFS_STAT
						TrustPackedRefsStat.UNSET);
