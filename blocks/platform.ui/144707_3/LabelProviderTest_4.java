	private static final long TIMEOUT_DECORATOR = 2000;
	private static final long TIMEOUT_UPDATE_JOB = 500;

	private static final String DECORATION_TEXT_1 = "**1**";
	private static final String DECORATION_TEXT_2 = "**2**";
	private static final String DECORATION_TEXT_3 = "**3**";

	private static final DisplayHelper waitForDecoration = new DisplayHelper() {
		@Override
		protected boolean condition() {
			try {
				return Bug417255Decorator.hasRun(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}
	};

