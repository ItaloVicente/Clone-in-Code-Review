
		if (menuManager.getRemoveAllWhenShown()) {
			trace("Cleaning up all of the menu model items" + menuManager, menuManager.getMenu(), menuModel); //$NON-NLS-1$
			renderer.cleanUp(menuModel);

			MMenuElement[] menuContributionsToRemove = menuModel.getChildren()
					.toArray(new MMenuElement[menuModel.getChildren().size()]);
			for (MMenuElement mMenuElement : menuContributionsToRemove) {
				menuModel.getChildren().remove(mMenuElement);
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
