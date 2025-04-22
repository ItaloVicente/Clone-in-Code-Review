
	public enum PushDefault implements Config.ConfigEnum {

		NOTHING

		CURRENT

		UPSTREAM("tracking")

		SIMPLE

		MATCHING;

		private final String alias;

		private PushDefault() {
			alias = null;
		}

		private PushDefault(String alias) {
			this.alias = alias;
		}

		@Override
		public String toConfigValue() {
			return name().toLowerCase(Locale.ROOT);
		}

		@Override
		public boolean matchConfigValue(String in) {
			return toConfigValue().equalsIgnoreCase(in)
					|| (alias != null && alias.equalsIgnoreCase(in));
		}
	}

	private final PushRecurseSubmodulesMode recurseSubmodules;

	private final PushDefault pushDefault;

	public PushConfig(Config config) {
		recurseSubmodules = config.getEnum(ConfigConstants.CONFIG_PUSH_SECTION
				null
				PushRecurseSubmodulesMode.NO);
		pushDefault = config.getEnum(ConfigConstants.CONFIG_PUSH_SECTION
				ConfigConstants.CONFIG_KEY_DEFAULT
	}

	public PushRecurseSubmodulesMode getRecurseSubmodules() {
		return recurseSubmodules;
	}

	public PushDefault getPushDefault() {
		return pushDefault;
	}
