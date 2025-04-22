			new SectionParser<SignedPushConfig>() {
		@Override
		public SignedPushConfig parse(Config cfg) {
			return new SignedPushConfig(cfg);
		}
	};
