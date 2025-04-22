		}
		if (!oldMenus.isEmpty()) {
			modelChildren.removeAll(oldMenus);
			for (MMenu oldMenu : oldMenus) {
				MenuManager oldManager = getManager(oldMenu);
				clearModelToManager(oldMenu, oldManager);
