	public static void runOnce(Display d, Runnable r) {
		if (d.isDisposed()) {
			return;
		}
		WorkQueue queue = getQueueFor(d);
		queue.runOnce(r);
	}
