	 * IRunnableWithProgress) blocks until the runnable has been run, regardless
	 * of the value of <code>fork</code>. It is recommended that
	 * <code>fork</code> is set to true in most cases. If <code>fork</code>
	 * is set to <code>false</code>, the runnable will run in the UI thread
	 * and it is the runnable's responsibility to call
	 * <code>Display.readAndDispatch()</code> to ensure UI responsiveness.
