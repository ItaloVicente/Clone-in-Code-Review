		openTestWindow();
		WorkbenchWindow workbenchWindow = (WorkbenchWindow) getWorkbench().getActiveWorkbenchWindow();
		MWindow window = workbenchWindow.getModel();
		EModelService modelService = window.getContext().get(EModelService.class);
		MToolControl control = (MToolControl) modelService.find("SearchField", window); //$NON-NLS-1$
		searchField = (SearchField) control.getObject();
		assertNotNull("Search Field must exist", searchField);
