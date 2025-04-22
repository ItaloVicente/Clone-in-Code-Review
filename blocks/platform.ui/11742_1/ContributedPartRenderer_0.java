
	private void unlinkMenu(MMenu menu) {
		AbstractPartRenderer abstractRenderer = factory.getRenderer(menu, null);
		if ((abstractRenderer == null)
				|| (!(abstractRenderer instanceof MenuManagerRenderer)))
			return;
		MenuManagerRenderer renderer = (MenuManagerRenderer) abstractRenderer;
		List<MMenuElement> children = menu.getChildren();
		for (MMenuElement child : children) {
			if (child instanceof MMenu)
				unlinkMenu((MMenu) child);
			else {
				IContributionItem contribution = renderer
						.getContribution(child);
				renderer.clearModelToContribution(child, contribution);
			}
		}
		MenuManager mm = renderer.getManager(menu);
		renderer.clearModelToManager(menu, mm);
	}

