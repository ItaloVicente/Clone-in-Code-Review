	public ProjectPropertyDialogAction(IWorkbenchWindow window) {
		super(""); //$NON-NLS-1$
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		setText(IDEWorkbenchMessages.Workbench_projectProperties);
		setToolTipText(IDEWorkbenchMessages.Workbench_projectPropertiesToolTip);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IIDEHelpContextIds.PROJECT_PROPERTY_DIALOG_ACTION);
		workbenchWindow.getSelectionService().addSelectionListener(this);
		workbenchWindow.getPartService().addPartListener(this);
		setActionDefinitionId("org.eclipse.ui.project.properties"); //$NON-NLS-1$
	}
