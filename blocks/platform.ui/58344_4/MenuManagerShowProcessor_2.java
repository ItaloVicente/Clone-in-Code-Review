	private void addContextToDynamicElements(MMenu menuModel) {
		MMenuElement[] ml = menuModel.getChildren().toArray(new MMenuElement[menuModel.getChildren().size()]);
		for (int i = 0; i < ml.length; i++) {
			MMenuElement currentMenuElement = ml[i];
			if (currentMenuElement instanceof MDynamicMenuContribution) {
				addContext(menuModel, currentMenuElement);
			}
		}
	}

	private void addContext(MUIElement parent, MUIElement element) {
		IEclipseContext alContext = modelService.getContainingContext(parent).getActiveLeaf();
		MApplicationElement alElement = alContext.get(MApplicationElement.class);
		element.getTransientData().put(ModelUtils.CONTAINING_PARENT, alElement);
	}

	private void cleanUp(final Menu menu) {
