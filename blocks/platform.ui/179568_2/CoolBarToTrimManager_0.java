		List<MToolBarElement> oldItems = new ArrayList<>(container.getChildren());

		boolean needUpdate = fill(container, manager, oldItems);

		oldItems.removeIf(not(MUIElement::isToBeRendered));
		container.getChildren().removeAll(oldItems);

		return needUpdate;
	}

	private boolean fill(MToolBar container, IContributionManager manager, List<MToolBarElement> oldItems) {
