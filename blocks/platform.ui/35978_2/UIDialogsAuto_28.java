    	IWorkbench workbench = getWorkbench();

    	Shell shell = workbench.getActiveWorkbenchWindow().getShell();
		IEclipseContext ctx = workbench.getService(IEclipseContext.class);
		EModelService modelService = workbench.getService(EModelService.class);
		MApplication app = workbench.getService(MApplication.class);
		MWindow window = workbench.getService(MWindow.class);
        Dialog dialog = new ShowViewDialog(shell, app,window, modelService, ctx);
