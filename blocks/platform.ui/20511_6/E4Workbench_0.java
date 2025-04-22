	private static void rememberPerspectiveState(MApplication appModel, IEclipseContext appContext) {
		if (appModel == null || appContext == null)
			return;

		EModelService modelService = appContext.get(EModelService.class);
		if (modelService == null)
			return;

		List<MPerspective> allPerspectives = modelService.findElements(appModel, null,
				MPerspective.class, null);

		for (MPerspective perspective : allPerspectives) {
			if (modelService.findSnippet(appModel, perspective.getElementId()) == null) {
				modelService.cloneElement(perspective, appModel, false);
			}
		}
	}
