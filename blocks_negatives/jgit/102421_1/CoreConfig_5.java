	public static final Config.SectionParser<CoreConfig> KEY = new SectionParser<CoreConfig>() {
		@Override
		public CoreConfig parse(final Config cfg) {
			return new CoreConfig(cfg);
		}
	};
