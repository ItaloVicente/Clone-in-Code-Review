
	private void reportInterruption(Runnable runnable) {
		Thread nonUiThread = Thread.currentThread();

		String msg = "To avoid deadlock while executing Display.syncExec() with argument: " //$NON-NLS-1$
				+ runnable + ", thread " + nonUiThread.getName() //$NON-NLS-1$
				+ " will interrupt UI thread."; //$NON-NLS-1$
		MultiStatus main = new MultiStatus(WorkbenchPlugin.PI_WORKBENCH, IStatus.WARNING, msg,
				new IllegalStateException());

		ThreadInfo[] threads = ManagementFactory.getThreadMXBean().getThreadInfo(new long[] { nonUiThread.getId(), display.getThread().getId() }, true, true);

		for (ThreadInfo info : threads) {
			String childMsg;
			if (info.getThreadId() == nonUiThread.getId()) {
				childMsg = nonUiThread.getName() + " thread is an instance of Worker or owns an ILock"; //$NON-NLS-1$
			} else {
				childMsg = "UI thread waits on a lock."; //$NON-NLS-1$
			}
			childMsg += " Stack: \n" + info.toString(); //$NON-NLS-1$
			Status child = new Status(IStatus.INFO, WorkbenchPlugin.PI_WORKBENCH, IStatus.INFO, childMsg, null);
			main.add(child);
		}

		WorkbenchPlugin.log(main);
	}
