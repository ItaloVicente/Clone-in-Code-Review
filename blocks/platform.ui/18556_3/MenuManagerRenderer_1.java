	protected void handleMenuElementAdd(MenuManager manager,
			MMenuElement menuElement) {
		modelProcessSwitch(manager, menuElement);
	}

	protected void handleMenuElementRemove(MenuManager manager,
			MMenuElement menuElement) {
		if (menuElement instanceof MMenu) {
			MMenu menuModel = (MMenu) menuElement;
			manager.remove(getManager(menuModel));
		} else
			manager.remove(getContribution(menuElement));
		manager.update(false);
	}

