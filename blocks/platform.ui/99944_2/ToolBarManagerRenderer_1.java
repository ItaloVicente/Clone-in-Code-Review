		for (MToolBarElement element : model.getChildren()) {
			IContributionItem ici = getContribution(element);
			clearModelToContribution(element, ici);
		}

		ToolBarManager removed = modelToManager.remove(model);
		if (manager == null) {
			managerToModel.remove(removed);
		} else {
			managerToModel.remove(manager);
		}

