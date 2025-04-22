		private static Duration measureClockResolution() {
			Duration clockResolution = Duration.ZERO;
			for (int i = 0; i < 10; i++) {
				Instant t1 = Instant.now();
				Instant t2 = t1;
				while (t2.compareTo(t1) <= 0) {
					t2 = Instant.now();
				}
				Duration r = Duration.between(t1
				if (r.compareTo(clockResolution) > 0) {
					clockResolution = r;
				}
			}
			return clockResolution;
		}

		private static void deleteProbe(Path probe) {
			try {
				FileUtils.delete(probe.toFile()
						FileUtils.SKIP_MISSING | FileUtils.RETRY);
			} catch (IOException e) {
				LOG.error(e.getMessage()
			}
		}

		private static Optional<FileStoreAttributes> readFromConfig(
				FileStore s) {
			FileBasedConfig userConfig = SystemReader.getInstance()
					.openUserConfig(null
			try {
				userConfig.load(false);
			} catch (IOException e) {
				LOG.error(MessageFormat.format(JGitText.get().readConfigFailed
						userConfig.getFile().getAbsolutePath())
			} catch (ConfigInvalidException e) {
				LOG.error(MessageFormat.format(
						JGitText.get().repositoryConfigFileInvalid
						userConfig.getFile().getAbsolutePath()
						e.getMessage()));
			}
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
			FileStoreAttributes c = new FileStoreAttributes(resolution);
			if (!UNDEFINED_DURATION.equals(minRacyThreshold)) {
				c.minimalRacyInterval = minRacyThreshold;
			}
			return Optional.of(c);
		}

		private static void saveToConfig(FileStore s
				FileStoreAttributes c) {
			FileBasedConfig userConfig = SystemReader.getInstance()
					.openUserConfig(null
			long resolution = c.getFsTimestampResolution().toNanos();
			TimeUnit resolutionUnit = getUnit(resolution);
			long resolutionValue = resolutionUnit.convert(resolution
					TimeUnit.NANOSECONDS);

			long minRacyThreshold = c.getMinimalRacyInterval().toNanos();
			TimeUnit minRacyThresholdUnit = getUnit(minRacyThreshold);
			long minRacyThresholdValue = minRacyThresholdUnit
					.convert(minRacyThreshold

			final int max_retries = 5;
			int retries = 0;
			boolean succeeded = false;
			String key = getConfigKey(s);
			while (!succeeded && retries < max_retries) {
				try {
					userConfig.load(false);
					userConfig.setString(
							ConfigConstants.CONFIG_FILESYSTEM_SECTION
							ConfigConstants.CONFIG_KEY_TIMESTAMP_RESOLUTION
							String.format("%d %s"
									Long.valueOf(resolutionValue)
									resolutionUnit.name().toLowerCase()));
					userConfig.setString(
							ConfigConstants.CONFIG_FILESYSTEM_SECTION
							ConfigConstants.CONFIG_KEY_MIN_RACY_THRESHOLD
							String.format("%d %s"
									Long.valueOf(minRacyThresholdValue)
									minRacyThresholdUnit.name().toLowerCase()));
					userConfig.save();
					succeeded = true;
				} catch (LockFailedException e) {
					try {
						LOG.warn(MessageFormat.format(JGitText.get().cannotLock
								userConfig.getFile().getAbsolutePath()));
						retries++;
						Thread.sleep(20);
					} catch (InterruptedException e1) {
						Thread.interrupted();
					}
				} catch (IOException e) {
					LOG.error(MessageFormat.format(
							JGitText.get().cannotSaveConfig
							userConfig.getFile().getAbsolutePath())
				} catch (ConfigInvalidException e) {
					LOG.error(MessageFormat.format(
							JGitText.get().repositoryConfigFileInvalid
							userConfig.getFile().getAbsolutePath()
							e.getMessage()));
				}
			}
		}

		private static String getConfigKey(FileStore s) {
			final String storeKey;
			if (SystemReader.getInstance().isWindows()) {
				Object attribute = null;
				try {
				} catch (IOException ignored) {
				}
				if (attribute instanceof Integer) {
					storeKey = attribute.toString();
				} else {
					storeKey = s.name();
				}
			} else {
				storeKey = s.name();
			}
			return javaVersionPrefix + storeKey;
		}

		private static TimeUnit getUnit(long nanos) {
			TimeUnit unit;
			if (nanos < 200_000L) {
				unit = TimeUnit.NANOSECONDS;
			} else if (nanos < 200_000_000L) {
				unit = TimeUnit.MICROSECONDS;
			} else {
				unit = TimeUnit.MILLISECONDS;
			}
			return unit;
		}

		private final @NonNull Duration fsTimestampResolution;

		private Duration minimalRacyInterval;

		public Duration getMinimalRacyInterval() {
			return minimalRacyInterval;
		}

		@NonNull
		public Duration getFsTimestampResolution() {
			return fsTimestampResolution;
		}

		public FileStoreAttributes(
				@NonNull Duration fsTimestampResolution) {
			this.fsTimestampResolution = fsTimestampResolution;
			this.minimalRacyInterval = Duration.ZERO;
		}

		@SuppressWarnings({ "nls"
