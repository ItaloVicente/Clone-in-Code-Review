
		for (Map.Entry<IContributionItem, MToolBarElement> entry : contributionToModel.entrySet()) {
			processVisibility(entry.getKey(), entry.getValue());
		}
		for (ToolBarManager mgr : managerToModel.keySet()) {
			mgr.update(false);
		}

