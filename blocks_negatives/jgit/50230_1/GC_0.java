		long expireDate = Long.MAX_VALUE;

		if (expire == null && expireAgeMillis == -1) {
			String pruneExpireStr = repo.getConfig().getString(
					ConfigConstants.CONFIG_GC_SECTION, null,
					ConfigConstants.CONFIG_KEY_PRUNEEXPIRE);
			if (pruneExpireStr == null)
				pruneExpireStr = PRUNE_EXPIRE_DEFAULT;
			expire = GitDateParser.parse(pruneExpireStr, null, SystemReader
					.getInstance().getLocale());
			expireAgeMillis = -1;
		}
		if (expire != null)
			expireDate = expire.getTime();
		if (expireAgeMillis != -1)
			expireDate = System.currentTimeMillis() - expireAgeMillis;
