				setDirty(false);
			}
		} else {
			if (recursive) {
				IContributionItem[] items = getItems();
				for (IContributionItem ci : items) {
					if (ci instanceof IMenuManager) {
						IMenuManager mm = (IMenuManager) ci;
						if (isChildVisible(mm)) {
							mm.updateAll(force);
						}
					}
				}
			}
		}
		updateMenuItem();
	}

	@Override
