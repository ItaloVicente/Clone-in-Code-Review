	 * @param parentShell
	 *            The parent shell, or <code>null</code> to create a top-level
	 *            shell. If the parentShell is not null we will open immediately
	 *            as parenting has been determined. If it is <code>null</code>
	 *            then the dialog will not open until there is no modal shell
	 *            blocking it.
	 * @param blockedMonitor
	 *            The monitor that is currently blocked
	 * @param reason
	 *            A status describing why the monitor is blocked
	 * @param taskName
	 *            A name to give the blocking task in the dialog
