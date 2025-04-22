	private Instant getLogExpiry() throws ParseException {
		if (gcLogExpire == null) {
			String logExpiryStr = getConfig().getString(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_LOGEXPIRY);
			if (logExpiryStr == null)
				logExpiryStr = LOG_EXPIRY_DEFAULT;
			gcLogExpire = GitDateParser.parse(logExpiryStr
					.getInstance().getLocale()).toInstant();
		}
		return gcLogExpire;
	}

	public boolean gcAutoDetach() {
		return getConfig().getBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTODETACH
	}

