	@SuppressWarnings("boxing")
	@Override
	public void createPartControl(Composite aParent) {
		super.createPartControl(aParent);

		IWorkbenchWindow w = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		ICommandService csrv = (ICommandService) w
				.getService(ICommandService.class);
		Command command = csrv
				.getCommand("org.eclipse.egit.ui.RepositoriesLinkWithSelection"); //$NON-NLS-1$
		reactOnSelection = (Boolean) command.getState(
				RegistryToggleState.STATE_ID).getValue();
	}

