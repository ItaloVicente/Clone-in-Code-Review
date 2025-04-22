	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		IWorkbench workbench = openTestWindow().getWorkbench();
		commandService = workbench.getAdapter(ICommandService.class);
		bindingService = workbench.getAdapter(IBindingService.class);
	}

