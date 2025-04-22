	public enum BranchRebaseMode implements Config.ConfigEnum {

		REBASE("true")
		PRESERVE("preserve")
		INTERACTIVE("interactive")

		private final String configValue;

		private BranchRebaseMode(String configValue) {
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

