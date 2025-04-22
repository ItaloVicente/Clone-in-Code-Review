
		if (modelService.find(IWorkbenchConstants.ADDITIONS, trimBar) == null) {
			MToolBarSeparator additionsSeparator = MenuFactoryImpl.eINSTANCE
					.createToolBarSeparator();
			additionsSeparator.setToBeRendered(false);
			additionsSeparator.setElementId(IWorkbenchConstants.ADDITIONS);

			MToolBar additionsToolBar = MenuFactoryImpl.eINSTANCE.createToolBar();
			additionsToolBar.getTags().add(IWorkbenchConstants.TAG_TOOLBAR_SEPARATOR);
			additionsToolBar.setElementId(IWorkbenchConstants.ADDITIONS);
			additionsToolBar.setToBeRendered(false);
			additionsToolBar.getChildren().add(additionsSeparator);
			trimBar.getChildren().add(additionsToolBar);
		}

		MToolControl spacerControl = (MToolControl) modelService.find(
				IWorkbenchConstants.TRIM_PERSPECTIVE_SPACER, model);
