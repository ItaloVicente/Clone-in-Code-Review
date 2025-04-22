	private static final class CleanupThread extends Thread {
		private static final CleanupThread me;
		static {
			me = new CleanupThread();
			Runtime.getRuntime().addShutdownHook(me);
		}
