		workbenchTrimElements = new ArrayList<MTrimElement>();
		statusLineManager = new StatusLineManager();
		menuManager = new MenuManager("MenuBar", ActionSet.MAIN_MENU); //$NON-NLS-1$

		this.configurer = configurer;
		IRendererFactory rendererFactory = context.get(IRendererFactory.class);
		EModelService modelService = context.get(EModelService.class);

		windowModel = modelService.createModelElement(MTrimmedWindow.class);
		MApplication app = context.get(MApplication.class);
		windowModel.setContext(app.getContext().createChild("window - CustomizeActionBars")); //$NON-NLS-1$
		Shell shell = new Shell();
		windowModel.setWidget(shell);
		shell.setData(org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer.OWNING_ME, windowModel);

		mainMenu = modelService.createModelElement(MMenu.class);
		mainMenu.setElementId(ActionSet.MAIN_MENU);

		menuRenderer = (MenuManagerRenderer) rendererFactory.getRenderer(mainMenu, null);
		menuRenderer.linkModelToManager(mainMenu, menuManager);
		windowModel.setMainMenu(mainMenu);

		coolBarManager = new CoolBarToTrimManager(app, windowModel, workbenchTrimElements, rendererFactory);
