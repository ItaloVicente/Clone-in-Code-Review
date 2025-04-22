	private void configureShell(Shell shell, IEclipseContext context) {
		String title = getWindowConfigurer().basicGetTitle();
		if (title != null) {
			shell.setText(TextProcessor.process(title, TEXT_DELIMITERS));
		}
		workbench.getHelpSystem().setHelp(shell, IWorkbenchHelpContextIds.WORKBENCH_WINDOW);

		IContextService contextService = context.get(IContextService.class);
		contextService.registerShell(shell, IContextService.TYPE_WINDOW);
	}
	
