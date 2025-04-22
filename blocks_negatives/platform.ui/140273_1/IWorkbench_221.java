	 *            progress monitor while the save is taking place. Clients can
	 *            use a workbench window for this.
	 * @param filter the filter used to determine if a particular dirty saveable
	 *            needs to be saved or <code>null</code> if all dirty
	 *            saveables should be saved.
	 * @param confirm <code>true</code> to ask the user before saving unsaved
	 *            changes (recommended), and <code>false</code> to save
	 *            unsaved changes without asking
	 * @return <code>true</code> if the command succeeded, and
	 *         <code>false</code> if the operation was canceled by the user or
	 *         an error occurred while saving
	 */
	boolean saveAll(IShellProvider shellProvider,
			IRunnableContext runnableContext, ISaveableFilter filter,
