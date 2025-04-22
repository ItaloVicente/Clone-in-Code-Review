	private static enum HttpRedirectMode implements Config.ConfigEnum {

		TRUE("true")
		INITIAL("initial")

		private final String configValue;

		private HttpRedirectMode(String configValue) {
			this.configValue = configValue;
		}

		@Override
		public String toConfigValue() {
			return configValue;
		}

		@Override
		public boolean matchConfigValue(String s) {
			return configValue.equals(s);
		}
	}

