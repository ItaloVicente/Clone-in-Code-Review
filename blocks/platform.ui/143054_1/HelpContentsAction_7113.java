	public HelpContentsAction(IWorkbenchWindow window) {
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		setActionDefinitionId(IWorkbenchCommandConstants.HELP_HELP_CONTENTS);
