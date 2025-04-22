	private void disposeToolBarElement(ToolBarManager parentManager, MToolBarElement toolBarElement) {
		IContributionItem ici = getContribution(toolBarElement);
		if (ici == null) {
			return;
		}
		parentManager.remove(ici);
		clearModelToContribution(toolBarElement, ici);
		ici.dispose();
	}

