			String key = getConfigKey(s);
			Duration resolution = Duration.ofNanos(userConfig.getTimeUnit(
					ConfigConstants.CONFIG_FILESYSTEM_SECTION
					ConfigConstants.CONFIG_KEY_TIMESTAMP_RESOLUTION
					UNDEFINED_DURATION.toNanos()
			if (UNDEFINED_DURATION.equals(resolution)) {
				return Optional.empty();
			}
			Duration minRacyThreshold = Duration.ofNanos(userConfig.getTimeUnit(
					ConfigConstants.CONFIG_FILESYSTEM_SECTION
					ConfigConstants.CONFIG_KEY_MIN_RACY_THRESHOLD
					UNDEFINED_DURATION.toNanos()
			FileStoreAttributeCache c = new FileStoreAttributeCache(resolution);
			if (!UNDEFINED_DURATION.equals(minRacyThreshold)) {
				c.minimalRacyInterval = minRacyThreshold;
			}
			return Optional.of(c);
