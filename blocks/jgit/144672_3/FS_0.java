		private static Duration readFileTimeResolution(FileStore s) {
			FileBasedConfig userConfig = SystemReader.getInstance()
					.openUserConfig(null
			try {
				userConfig.load();
			} catch (IOException e) {
				LOG.error(MessageFormat.format(JGitText.get().readConfigFailed
						userConfig.getFile().getAbsolutePath())
			} catch (ConfigInvalidException e) {
				LOG.error(MessageFormat.format(
						JGitText.get().repositoryConfigFileInvalid
						userConfig.getFile().getAbsolutePath()
						e.getMessage()));
			}
			Duration configured = Duration
					.ofNanos(userConfig.getTimeUnit(
							ConfigConstants.CONFIG_FILESYSTEM_SECTION
							javaVersionPrefix + s.name()
							ConfigConstants.CONFIG_KEY_TIMESTAMP_RESOLUTION
							UNDEFINED_RESOLUTION.toNanos()
							TimeUnit.NANOSECONDS));
			return configured;
		}

		private static void saveFileTimeResolution(FileStore s
				Duration resolution) {
			FileBasedConfig userConfig = SystemReader.getInstance()
					.openUserConfig(null
			try {
				userConfig.load();
				long nanos = resolution.toNanos();
				TimeUnit unit;
				if (nanos < 200_000L) {
					unit = TimeUnit.NANOSECONDS;
				} else if (nanos < 200_000_000L) {
					unit = TimeUnit.MICROSECONDS;
				} else {
					unit = TimeUnit.MILLISECONDS;
				}
				long value = unit.convert(nanos
				userConfig.setString(ConfigConstants.CONFIG_FILESYSTEM_SECTION
						javaVersionPrefix + s.name()
						ConfigConstants.CONFIG_KEY_TIMESTAMP_RESOLUTION
						String.format("%d %s"
								Long.valueOf(value)
								unit.name().toLowerCase()));
				userConfig.save();
			} catch (IOException e) {
				LOG.error(MessageFormat.format(JGitText.get().cannotSaveConfig
						userConfig.getFile().getAbsolutePath())
			} catch (ConfigInvalidException e) {
				LOG.error(MessageFormat.format(
						JGitText.get().repositoryConfigFileInvalid
						userConfig.getFile().getAbsolutePath()
						e.getMessage()));
			}
		}

