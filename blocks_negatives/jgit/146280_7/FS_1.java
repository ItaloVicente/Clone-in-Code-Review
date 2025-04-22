			Duration configured = Duration
					.ofNanos(userConfig.getTimeUnit(
							ConfigConstants.CONFIG_FILESYSTEM_SECTION,
							javaVersionPrefix + s.name(),
							ConfigConstants.CONFIG_KEY_TIMESTAMP_RESOLUTION,
							UNDEFINED_RESOLUTION.toNanos(),
							TimeUnit.NANOSECONDS));
			return configured;
