			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MMenuElement added = (MMenuElement) o;
				handleMenuElementAdd(parentManager, added);
			}
		} else if (UIEvents.isREMOVE(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				MMenuElement removed = (MMenuElement) o;
				if (modelService.getElementLocation(removed) != EModelService.NOT_IN_UI) {
					handleMenuElementMove(parentManager, removed, -1);
				} else {
					handleMenuElementRemove(parentManager, removed);
				}
			}
		} else if (UIEvents.isMOVE(event)) {
			MMenuElement moved = (MMenuElement) event.getProperty(UIEvents.EventTags.NEW_VALUE);
			Integer newPos = (Integer) event.getProperty(UIEvents.EventTags.POSITION);

			handleMenuElementMove(parentManager, moved, newPos);
		}
		updateWidget(parentManager);
	}

	private void handleMenuElementAdd(MenuManager menuManager, MMenuElement addedElement) {
		modelProcessSwitch(menuManager, addedElement);
	}

	private void handleMenuElementMove(MenuManager menuManager, MMenuElement movedElement, Integer newPos) {
		IContributionItem ici;
		if (movedElement instanceof MMenu) {
			ici = getManager((MMenu) movedElement);
		} else {
			ici = getContribution(movedElement);
		}
		if (ici != null) {
			menuManager.remove(ici);
			if (newPos >= 0) {
				menuManager.insert(newPos, ici);
			}
