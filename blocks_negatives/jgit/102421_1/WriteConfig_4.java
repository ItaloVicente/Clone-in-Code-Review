	static final Config.SectionParser<WriteConfig> KEY = new SectionParser<WriteConfig>() {
		@Override
		public WriteConfig parse(final Config cfg) {
			return new WriteConfig(cfg);
		}
	};
