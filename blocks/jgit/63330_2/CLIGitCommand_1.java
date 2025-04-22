	static class TestCmdLineParser extends CmdLineParser {
		public TestCmdLineParser(Object bean) {
			super(bean);
		}

		@Override
		protected boolean containsHelp(String... args) {
			return false;
		}
	}
