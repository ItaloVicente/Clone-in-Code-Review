	public static final Config.SectionParser<TransferConfig> KEY = new SectionParser<TransferConfig>() {
		@Override
		public TransferConfig parse(final Config cfg) {
			return new TransferConfig(cfg);
		}
	};
