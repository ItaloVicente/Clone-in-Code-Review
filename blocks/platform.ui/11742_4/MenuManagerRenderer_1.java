
	private void unlinkMenu(MMenu menu) {

		List<MMenuElement> children = menu.getChildren();
		for (MMenuElement child : children) {
			if (child instanceof MMenu)
				unlinkMenu((MMenu) child);
			else {
				IContributionItem contribution = getContribution(child);
				clearModelToContribution(child, contribution);
			}
		}
		MenuManager mm = getManager(menu);
		clearModelToManager(menu, mm);
	}
