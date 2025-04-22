		allowTipSha1InWant = rc.getBoolean(
				"uploadpack", "allowtipsha1inwant", false);
		allowReachableSha1InWant = rc.getBoolean(
				"uploadpack", "allowreachablesha1inwant", false);
		allowFilter = rc.getBoolean(
				"uploadpack", "allowfilter", false);
		protocolVersion = ProtocolVersion.parse(rc
				.getString(ConfigConstants.CONFIG_PROTOCOL_SECTION, null,
						ConfigConstants.CONFIG_KEY_VERSION));
