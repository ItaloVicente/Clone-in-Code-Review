		if (!oldModelItems.isEmpty()) {
			modelChildren.removeAll(oldModelItems);
			for (MMenuItem model : oldModelItems) {
				IContributionItem ici = (IContributionItem) OpaqueElementUtil.getOpaqueItem(model);
				clearModelToContribution(model, ici);
			}
		}
		if (!oldMenus.isEmpty()) {
			modelChildren.removeAll(oldMenus);
			for (MMenu oldMenu : oldMenus) {
				MenuManager oldManager = getManager(oldMenu);
				clearModelToManager(oldMenu, oldManager);
			}
		}
		if (!oldSeps.isEmpty()) {
			modelChildren.removeAll(oldSeps);
			for (MMenuSeparator model : oldSeps) {
				IContributionItem item = (IContributionItem) OpaqueElementUtil.getOpaqueItem(model);
				clearModelToContribution(model, item);
			}
		}
