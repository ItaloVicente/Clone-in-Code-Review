	private void addContextToDynamicElements(MMenu menuModel) {
		MMenuElement[] ml = menuModel.getChildren().toArray(new MMenuElement[menuModel.getChildren().size()]);
		for (int i = 0; i < ml.length; i++) {
			MMenuElement currentMenuElement = ml[i];
			if (currentMenuElement instanceof MDynamicMenuContribution) {
				currentMenuElement.getTransientData().put(DYNAMIC_MENU_CONTEXT, application);
			}
		}
	}

	private void cleanUp(final Menu menu) {
