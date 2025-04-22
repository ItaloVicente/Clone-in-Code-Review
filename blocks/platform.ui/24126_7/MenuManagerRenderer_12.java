
	private void handleMenuElementAdd(MenuManager manager,
			Iterable<MMenuElement> menuElements) {
		Iterator<MMenuElement> iterMenuElements = menuElements.iterator();

		if (!iterMenuElements.hasNext())
			return;

		while (iterMenuElements.hasNext()) {
			modelProcessSwitch(manager, iterMenuElements.next());
		}

		manager.updateAll(false);
	}

	private void handleMenuElementRemove(MenuManager manager,
			Iterable<MMenuElement> menuElements) {
		Iterator<MMenuElement> iterMenuElements = menuElements.iterator();

		if (!iterMenuElements.hasNext())
			return;

		while (iterMenuElements.hasNext()) {
			MMenuElement menuElement = iterMenuElements.next();
			if (menuElement instanceof MMenu) {
				MMenu menuModel = (MMenu) menuElement;
				MenuManager mi = getManager(menuModel);
				manager.remove(mi);

				clearModelToManager(menuModel, mi);
				handleMenuElementRemove(mi, menuModel.getChildren());
				mi.dispose();
			} else {
				IContributionItem ci = getContribution(menuElement);
				manager.remove(ci);

				clearModelToContribution(menuElement, ci);
				ci.dispose();
			}

		}

		manager.updateAll(false);
	}
