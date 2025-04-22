
	private static class JschBugFixingConfigRepository
			implements ConfigRepository {

		private final ConfigRepository base;

		public JschBugFixingConfigRepository(ConfigRepository base) {
			this.base = base;
		}

		@Override
		public Config getConfig(String host) {
			return new JschBugFixingConfig(base.getConfig(host));
		}

		private static class JschBugFixingConfig implements Config {

			private static final String[] NO_IDENTITIES = {};

			private final Config real;

			public JschBugFixingConfig(Config delegate) {
				real = delegate;
			}

			@Override
			public String getHostname() {
				return real.getHostname();
			}

			@Override
			public String getUser() {
				return real.getUser();
			}

			@Override
			public int getPort() {
				return real.getPort();
			}

			@Override
			public String getValue(String key) {
				String k = key.toUpperCase(Locale.ROOT);
					return null;
				}
				String result = real.getValue(key);
				if (result != null) {
						try {
							int timeout = Integer.parseInt(result);
							result = Long.toString(
									TimeUnit.SECONDS.toMillis(timeout));
						} catch (NumberFormatException e) {
						}
					}
				}
				return result;
			}

			@Override
			public String[] getValues(String key) {
				String k = key.toUpperCase(Locale.ROOT);
					return NO_IDENTITIES;
				}
				return real.getValues(key);
			}
		}
	}
