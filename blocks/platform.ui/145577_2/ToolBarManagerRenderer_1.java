	private void removeElement(ToolBarManager parentManager, MToolBarElement toolBarElement) {
		IContributionItem ici = getContribution(toolBarElement);
		clearModelToContribution(toolBarElement, ici);
		if (ici != null && parentManager != null) {
			ici = parentManager.remove(ici);
		}
		if (ici != null) {
			ici.dispose();
		}
	}

