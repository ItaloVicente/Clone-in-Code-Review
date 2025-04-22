	private void disposeToolBarElement(ToolBarManager parentManager, MToolBarElement toolBarElement) {
		IContributionItem ici = getContribution(toolBarElement);
		if (ici == null) {
			return;
		}
		parentManager.remove(ici);
		ici.dispose();
		clearModelToContribution(toolBarElement, ici);
	}

