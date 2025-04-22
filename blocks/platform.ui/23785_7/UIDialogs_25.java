
    	IWorkbench workbench = getWorkbench();

    	Shell shell = workbench.getActiveWorkbenchWindow().getShell();
		IEclipseContext ctx = (IEclipseContext) workbench.getService(IEclipseContext.class);
		EModelService modelService = (EModelService) workbench.getService(EModelService.class);
		MApplication app = (MApplication) workbench.getService(MApplication.class);
		MWindow window = (MWindow) workbench.getService(MWindow.class);
        Dialog dialog = new ShowViewDialog(shell, app,window, modelService, ctx);
