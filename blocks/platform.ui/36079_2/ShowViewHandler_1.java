	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Shell shell = HandlerUtil.getActiveShell(event);
		IEclipseContext ctx = workbenchWindow.getService(IEclipseContext.class);
		EModelService modelService = workbenchWindow.getService(EModelService.class);
		EPartService partService = workbenchWindow.getService(EPartService.class);
		MApplication app = workbenchWindow.getService(MApplication.class);
		MWindow window = workbenchWindow.getService(MWindow.class);

