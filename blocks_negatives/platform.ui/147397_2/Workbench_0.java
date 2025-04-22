		EModelService modelService = context.get(EModelService.class);

		List<MPart> parts = modelService.findElements(appCopy, null, MPart.class);
		for (MPart part : parts) {
			for (MMenu menu : part.getMenus()) {
				menu.getChildren().clear();
			}
			MToolBar tb = part.getToolbar();
			if (tb != null) {
				tb.getChildren().clear();
			}
		}
