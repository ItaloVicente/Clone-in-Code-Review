			if (ici == null && mMenuElement instanceof MMenu) {
				MMenu menuElement = (MMenu) mMenuElement;
				ici = getManager(menuElement);
				clearModelToManager(menuElement, (MenuManager) ici);
			} else {
				clearModelToContribution(menuModel, ici);
			}
