
		List<MToolBarElement> childrenToInspect;
		if (toolbarContribution.getTransientData().get(FACTORY) != null) {
			childrenToInspect = toolbarModel.getChildren();
		} else {
			childrenToInspect = toolbarContribution.getChildren();
		}

		for (MToolBarElement child : childrenToInspect) {
