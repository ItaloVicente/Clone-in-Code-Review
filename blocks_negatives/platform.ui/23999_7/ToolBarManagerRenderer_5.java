		return managerToModel.get(manager);
	}

	public void linkModelToManager(MToolBar model, ToolBarManager manager) {
		modelToManager.put(model, manager);
		managerToModel.put(manager, model);
	}

	public void clearModelToManager(MToolBar model, ToolBarManager manager) {
		modelToManager.remove(model);
		managerToModel.remove(manager);
	}

	public IContributionItem getContribution(MToolBarElement element) {
		return modelToContribution.get(element);
