		MenuManager remove = modelToManager.remove(model);
		if (manager == null) {
			managerToModel.remove(remove);
		} else {
			managerToModel.remove(manager);
		}

