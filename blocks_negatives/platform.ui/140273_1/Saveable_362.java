	 * @param monitor
	 *            a progress monitor used for reporting progress and
	 *            cancellation
	 * @param shellProvider
	 *            an object that can provide a shell for parenting dialogs
	 * @return <code>null</code> if this saveable has been saved successfully,
	 *         or a job runnable that needs to be run to complete the save in
	 *         the background.
	 * @throws CoreException
	 *             if the save fails; it is the caller's responsibility to
	 *             report the failure to the user
