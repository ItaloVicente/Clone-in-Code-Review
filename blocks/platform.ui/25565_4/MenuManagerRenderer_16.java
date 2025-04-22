
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
				if (mi == null) {
					continue;
				}
				manager.remove(mi);

				clearModelToManager(menuModel, mi);
				handleMenuElementRemove(mi, menuModel.getChildren());
				mi.dispose();
			} else {
				IContributionItem ci = getContribution(menuElement);
				if (ci == null) {
					continue;
				}
				manager.remove(ci);

				clearModelToContribution(menuElement, ci);
				ci.dispose();
			}
		}

		if (!disposingMenuUIElement) {
			manager.updateAll(false);
		}
	}
