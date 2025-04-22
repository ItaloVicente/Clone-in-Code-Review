		boolean trustFolderStat = getRepository().getConfig().getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION,
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT, true);
		TrustPackedRefsStat trustPackedRefsStat =
				getRepository().getConfig().getEnum(
						ConfigConstants.CONFIG_CORE_SECTION,
						null,
						ConfigConstants.CONFIG_KEY_TRUST_PACKED_REFS_STAT,
						TrustPackedRefsStat.UNSET);

