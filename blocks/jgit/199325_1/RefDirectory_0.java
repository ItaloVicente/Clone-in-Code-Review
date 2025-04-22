		trustFolderStat = getRepository().getConfig()
				.getBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
		trustPackedRefsStat = getRepository().getConfig()
				.getEnum(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_TRUST_PACKED_REFS_STAT
						TrustPackedRefsStat.UNSET);
