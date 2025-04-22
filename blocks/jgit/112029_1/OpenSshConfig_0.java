
	private static class JschBugFixingConfig implements Config {

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
			String result = real.getValue(key);
			if (result != null) {
				String k = key.toUpperCase(Locale.ROOT);
					try {
						int timeout = Integer.parseInt(result);
						result = Long
								.toString(TimeUnit.SECONDS.toMillis(timeout));
					} catch (NumberFormatException e) {
					}
				}
			}
			return result;
		}

		@Override
		public String[] getValues(String key) {
			return real.getValues(key);
		}

	}
