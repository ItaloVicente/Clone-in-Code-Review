		return managerToModel.get(manager);
	}

	public void linkModelToManager(MMenu model, MenuManager manager) {
		modelToManager.put(model, manager);
		managerToModel.put(manager, model);
	}

	public void clearModelToManager(MMenu model, MenuManager manager) {
		modelToManager.remove(model);
		managerToModel.remove(manager);
	}

	public IContributionItem getContribution(MMenuElement model) {
		return modelToContribution.get(model);
