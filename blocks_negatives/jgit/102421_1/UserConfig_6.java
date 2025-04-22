	public static final Config.SectionParser<UserConfig> KEY = new SectionParser<UserConfig>() {
		@Override
		public UserConfig parse(final Config cfg) {
			return new UserConfig(cfg);
		}
	};
