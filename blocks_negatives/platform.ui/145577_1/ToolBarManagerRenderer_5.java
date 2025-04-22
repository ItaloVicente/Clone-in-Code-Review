		if (!oldModelItems.isEmpty()) {
			modelChildren.removeAll(oldModelItems);
			for (MToolItem model : oldModelItems) {
				Object obj = OpaqueElementUtil.getOpaqueItem(model);
				clearModelToContribution(model, (IContributionItem) obj);
			}
		}
