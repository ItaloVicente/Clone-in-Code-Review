	public static final Config.SectionParser<DiffConfig> KEY = new SectionParser<DiffConfig>() {
		@Override
		public DiffConfig parse(final Config cfg) {
			return new DiffConfig(cfg);
		}
	};
