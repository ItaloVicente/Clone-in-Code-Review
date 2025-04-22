	private void handleMenuElementRemove(MenuManager menuManager, MMenuElement removedElement) {
		removedElement.setRenderer(null);

		IContributionItem ici;
		if (removedElement instanceof MMenu) {
			MMenu menuModel = (MMenu) removedElement;
			MenuManager removedManager = getManager(menuModel);

			cleanUp(menuModel);
			clearModelToManager(menuModel, removedManager);


			for (MMenuElement childME : menuModel.getChildren()) {
				handleMenuElementRemove(removedManager, childME);
			}

			ici = removedManager;
		} else {
			ici = getContribution(removedElement);
			clearModelToContribution(removedElement, ici);
		}
		if (ici != null && menuManager != null) {
			ici = menuManager.remove(ici);
		}
		if (ici != null) {
			ici.dispose();
		}
	}

	private void updateWidget(MenuManager manager) {
		manager.update(false);
	}

