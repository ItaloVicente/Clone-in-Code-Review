	private enum PullRebaseMode implements Config.ConfigEnum {
		REBASE_PRESERVE("preserve", true, true), //$NON-NLS-1$
		REBASE("true", true, false), //$NON-NLS-1$
		NO_REBASE("false", false, false); //$NON-NLS-1$

		private final String configValue;

		private final boolean rebase;

		private final boolean preserveMerges;

		PullRebaseMode(String configValue, boolean rebase,
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
	}

