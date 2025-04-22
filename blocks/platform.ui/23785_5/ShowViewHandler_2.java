	private final void openOther(final Shell shell, MApplication app, MWindow window,
			EModelService modelService,
			IEclipseContext context,
			EPartService partService) {

		final ShowViewDialog dialog = new ShowViewDialog(shell, app, window, modelService, context);
