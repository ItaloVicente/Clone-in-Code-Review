
		List<MToolBarElement> childrenToInspect;
		if (toolbarContribution.getTransientData().get(FACTORY) != null) {
			childrenToInspect = this.generatedElements;
		} else {
			childrenToInspect = toolbarContribution.getChildren();
		}

		for (MToolBarElement child : childrenToInspect) {
