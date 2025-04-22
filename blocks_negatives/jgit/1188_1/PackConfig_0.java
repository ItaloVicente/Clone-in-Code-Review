class PackConfig {
	/** Key for {@link Config#get(SectionParser)}. */
	static final Config.SectionParser<PackConfig> KEY = new SectionParser<PackConfig>() {
		public PackConfig parse(final Config cfg) {
			return new PackConfig(cfg);
		}
	};
