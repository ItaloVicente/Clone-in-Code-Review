	/**
	 * @see org.eclipse.core.commands.IStateListener#handleStateChange(org.eclipse.core.commands.State,
	 *      java.lang.Object)
	 */
	@Override
	public void handleStateChange(State state, Object oldValue) {
		try {
			this.verboseBranchMode = ((Boolean) state.getValue())
					.booleanValue();
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
