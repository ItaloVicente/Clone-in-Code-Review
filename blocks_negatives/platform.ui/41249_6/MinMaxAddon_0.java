		if (trimStack == null) {
			trimStack = MenuFactoryImpl.eINSTANCE.createToolControl();
			trimStack.setElementId(trimId);
			trimStack.setContributionURI(TrimStack.CONTRIBUTION_URI);

			MTrimBar bar = getBarForElement(element, window);
			int index = getCachedIndex(element);
			if (index == -1 || index >= bar.getChildren().size())
				bar.getChildren().add(trimStack);
			else
				bar.getChildren().add(index, trimStack);

			bar.setVisible(true);

			if (bar.getWidget() == null) {
				bar.setToBeRendered(true);

				context.get(IPresentationEngine.class)
						.createGui(bar, winShell, window.getContext());
			}
		} else {
			MUIElement parent = trimStack.getParent();
			parent.setVisible(true);
			if (parent.getWidget() == null) {
				parent.setToBeRendered(true);
				context.get(IPresentationEngine.class).createGui(parent, winShell,
						window.getContext());
