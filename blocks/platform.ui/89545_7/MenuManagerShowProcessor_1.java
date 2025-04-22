
		if (menuManager.getRemoveAllWhenShown()) {
			trace("\nCleaning up all of the menu model items", menuManager, menuModel); //$NON-NLS-1$
			renderer.cleanUp(menuModel);

			for (Iterator<MMenuElement> it = menuModel.getChildren().iterator(); it.hasNext();) {
				MMenuElement mMenuElement = it.next();
				it.remove();
				IContributionItem ici = renderer.getContribution(mMenuElement);
				if (ici == null && mMenuElement instanceof MMenu) {
					MMenu menuElement = (MMenu) mMenuElement;
					ici = renderer.getManager(menuElement);
					renderer.clearModelToManager(menuElement, (MenuManager) ici);
				} else {
					renderer.clearModelToContribution(menuModel, ici);
				}
				renderer.clearModelToContribution(mMenuElement, ici);
			}
		}
