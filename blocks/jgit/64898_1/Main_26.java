
	static class SubcommandLineParser extends CmdLineParser {
		public SubcommandLineParser(Object bean) {
			super(bean);
		}

		@Override
		protected boolean containsHelp(String... args) {
			return false;
		}
	}
