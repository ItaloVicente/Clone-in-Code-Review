
	private static class MockPrePushHook extends PrePushHook {

		private final PrintStream output;

		public MockPrePushHook(Repository repo
				PrintStream err) {
			super(repo
			output = out;
		}

		@Override
		protected void doRun() throws AbortedByHookException
			output.print(getStdinArgs());
		}
	}
