	private void addContextToDynamicElements(MMenu menuModel) {
		IEclipseContext alContext = modelService.getContainingContext(menuModel).getActiveLeaf();
		MApplicationElement alElement = alContext.get(MApplicationElement.class);

		MMenuElement[] ml = menuModel.getChildren().toArray(new MMenuElement[menuModel.getChildren().size()]);
		for (int i = 0; i < ml.length; i++) {
			MMenuElement currentMenuElement = ml[i];
			if (currentMenuElement instanceof MDynamicMenuContribution) {
				currentMenuElement.getTransientData().put(ModelUtils.CONTAINING_PARENT, alElement);
			}
		}
	}

	private void cleanUp(final Menu menu) {
