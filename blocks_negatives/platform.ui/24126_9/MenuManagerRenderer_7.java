		}
		if (!oldModelItems.isEmpty()) {
			modelChildren.removeAll(oldModelItems);
			for (MMenuItem model : oldModelItems) {
				IContributionItem ici = (IContributionItem) OpaqueElementUtil
						.getOpaqueItem(model);
				clearModelToContribution(model, ici);
