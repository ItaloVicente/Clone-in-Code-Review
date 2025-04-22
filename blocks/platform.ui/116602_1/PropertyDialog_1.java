	@Override
	protected void openImportWizard(Composite parent) {
		ICommandService service = ((IServiceLocator) PlatformUI.getWorkbench()).getService(ICommandService.class);
		Command command = service.getCommand("org.eclipse.ui.file.import"); //$NON-NLS-1$
		try {
			command.executeWithChecks(new ExecutionEvent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void openExportWizard(Composite parent) {
		ICommandService service = ((IServiceLocator) PlatformUI.getWorkbench()).getService(ICommandService.class);
		Command command = service.getCommand("org.eclipse.ui.file.export"); //$NON-NLS-1$
		try {
			command.executeWithChecks(new ExecutionEvent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

