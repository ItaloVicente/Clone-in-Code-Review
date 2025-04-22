	public static final Config.SectionParser<WorkingTreeOptions> KEY = new SectionParser<WorkingTreeOptions>() {
		@Override
		public WorkingTreeOptions parse(final Config cfg) {
			return new WorkingTreeOptions(cfg);
		}
	};
