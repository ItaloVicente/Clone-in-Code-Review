	private final State commandState;

	private boolean branchHierarchyMode = false;

	public RepositoriesViewContentProvider() {
		ICommandService srv = (ICommandService) PlatformUI.getWorkbench()
				.getService(ICommandService.class);
		commandState = srv.getCommand(
				"org.eclipse.egit.ui.RepositoriesToggleBranchHierarchy") //$NON-NLS-1$
				.getState("org.eclipse.ui.commands.toggleState"); //$NON-NLS-1$
		commandState.addListener(this);
		try {
			this.branchHierarchyMode = ((Boolean) commandState.getValue())
					.booleanValue();
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, false);
		}
	}

