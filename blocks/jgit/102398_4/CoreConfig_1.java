	public static final Config.SectionParser<CoreConfig> KEY =
			cfg -> new CoreConfig(cfg

	public static Config.SectionParser<CoreConfig> key(Repository repo) {
		return cfg -> new CoreConfig(cfg
	}
