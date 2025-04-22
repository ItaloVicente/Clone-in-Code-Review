			IRendererFactory rendererFactory = context.get(IRendererFactory.class);
			EModelService modelService = context.get(EModelService.class);

			windowModel = modelService.createModelElement(MTrimmedWindow.class);
			MApplication app = context.get(MApplication.class);
			windowModel.setContext(app.getContext().createChild("window - CustomizeActionBars")); //$NON-NLS-1$
			Shell value = new Shell();
			windowModel.setWidget(value);
			value.setData(org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer.OWNING_ME,
					windowModel);
			mainMenu = modelService.createModelElement(MMenu.class);
			mainMenu.setElementId("org.eclipse.ui.main.menu"); //$NON-NLS-1$

			menuRenderer = (MenuManagerRenderer) rendererFactory.getRenderer(
					mainMenu, null);
			menuRenderer.linkModelToManager(mainMenu, menuManager);

			coolbarToTrim = new CoolBarToTrimManager(app, windowModel, workbenchTrimElements,
					rendererFactory);
