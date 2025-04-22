
			if (item instanceof MMenu) {
				removeMenuContribution((MMenu) item);
			}
		}
	}

	private void removeMenuContribution(final MMenu menuModel) {
		clearModelToContribution(menuModel, modelToContribution.get(menuModel));

		if (menuModel.getChildren() != null) {
			for (MMenuElement child : menuModel.getChildren()) {
				if (child instanceof MMenu) {
					removeMenuContribution((MMenu) child);
				} else {
					clearModelToContribution(child, modelToContribution.get(child));
				}
			}
