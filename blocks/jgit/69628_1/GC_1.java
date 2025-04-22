	private long getPackExpireDate() throws ParseException {
		long packExpireDate = Long.MAX_VALUE;

		if (packExpire == null && packExpireAgeMillis == -1) {
			String prunePackExpireStr = repo.getConfig().getString(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_PRUNEPACKEXPIRE);
			if (prunePackExpireStr == null)
				prunePackExpireStr = PRUNE_PACK_EXPIRE_DEFAULT;
			packExpire = GitDateParser.parse(prunePackExpireStr
					SystemReader.getInstance().getLocale());
			packExpireAgeMillis = -1;
		}
		if (packExpire != null)
			packExpireDate = packExpire.getTime();
		if (packExpireAgeMillis != -1)
			packExpireDate = System.currentTimeMillis() - packExpireAgeMillis;
		return packExpireDate;
	}

