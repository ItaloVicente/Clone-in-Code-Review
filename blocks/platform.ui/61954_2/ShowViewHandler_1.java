	private static final void openOther(ExecutionEvent event, IWorkbenchWindow workbenchWindow, MApplication app,
			EPartService partService) {
		Shell shell = HandlerUtil.getActiveShell(event);
		IEclipseContext ctx = workbenchWindow.getService(IEclipseContext.class);
		EModelService modelService = workbenchWindow.getService(EModelService.class);
		MWindow window = workbenchWindow.getService(MWindow.class);
