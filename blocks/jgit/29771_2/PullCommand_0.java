	private enum PullRebaseMode implements Config.ConfigEnum {
		REBASE_PRESERVE("preserve"
		REBASE("true"
		NO_REBASE("false"

		private final String configValue;

		private final boolean rebase;

		private final boolean preserveMerges;

		PullRebaseMode(String configValue
				boolean preserveMerges) {
			this.configValue = configValue;
			this.rebase = rebase;
			this.preserveMerges = preserveMerges;
		}

		public String toConfigValue() {
			return configValue;
		}

		public boolean matchConfigValue(String in) {
			return in.equals(configValue);
		}
