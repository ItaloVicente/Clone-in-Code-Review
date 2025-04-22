		} else // I am not dirty. Check if I must recursivly walk down the hierarchy.
		if (recursive) {
			IContributionItem[] items = getItems();
			for (IContributionItem ci : items) {
				if (ci instanceof IMenuManager) {
					IMenuManager mm = (IMenuManager) ci;
					if (isChildVisible(mm)) {
						mm.updateAll(force);
