
		if (modelService.find(IWorkbenchConstants.ADDITIONS, trimBar) == null) {
			MToolBarSeparator additionsSeparator = modelService
					.createModelElement(MToolBarSeparator.class);
			additionsSeparator.setToBeRendered(false);
			additionsSeparator.setElementId(IWorkbenchConstants.ADDITIONS);

			MToolBar additionsToolBar = modelService.createModelElement(MToolBar.class);
			additionsToolBar.getTags().add(IWorkbenchConstants.TAG_TOOLBAR_SEPARATOR);
			additionsToolBar.setElementId(IWorkbenchConstants.ADDITIONS);
			additionsToolBar.setToBeRendered(false);
			additionsToolBar.getChildren().add(additionsSeparator);
			trimBar.getChildren().add(additionsToolBar);
		}

		MToolControl spacerControl = (MToolControl) modelService.find(
				IWorkbenchConstants.TRIM_PERSPECTIVE_SPACER, model);
