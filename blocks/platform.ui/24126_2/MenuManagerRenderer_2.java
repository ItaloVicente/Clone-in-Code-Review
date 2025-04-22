
	private void handleMenuElementAdd(MenuManager manager,
			List<MMenuElement> menuElements) {
		for (MMenuElement menuElement : menuElements) {
			modelProcessSwitch(manager, menuElement);
		}
	}

	private void handleMenuElementRemove(MenuManager manager,
			List<MMenuElement> menuElements) {
		if (menuElements.isEmpty())
			return;

		for (MMenuElement menuElement : menuElements) {
			if (menuElement instanceof MMenu) {
				MMenu menuModel = (MMenu) menuElement;
				manager.remove(getManager(menuModel));
			} else {
				manager.remove(getContribution(menuElement));
			}
		}
		manager.update(true);
	}
