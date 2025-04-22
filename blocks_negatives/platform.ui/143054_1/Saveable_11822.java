	 * controls, unless they are prepared to handle this case. This method is
	 * called on the UI thread after a job runnable has been returned from
	 * {@link #doSave(IProgressMonitor, IShellProvider)} and before
	 * spinning the event loop. The <code>closing</code> flag indicates that
	 * this saveable is currently being saved in response to closing a workbench
	 * part, in which case further changes to this saveable through the UI must
	 * be prevented.
