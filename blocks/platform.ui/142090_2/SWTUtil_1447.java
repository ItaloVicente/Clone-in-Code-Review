	public static void cancelExec(Display d, Runnable r) {
		if (d.isDisposed()) {
			return;
		}
		WorkQueue queue = getQueueFor(d);
		queue.cancelExec(r);
	}
