			dynamicEntry = createMenuEntry(parent, findDynamics, dynamicEntry, menuItem);
		}
	}

	private DynamicContributionItem createMenuEntry(DisplayItem parent,
			Map<IContributionItem, IContributionItem> findDynamics, DynamicContributionItem dynamicEntry,
			MMenuElement menuItem) {
		String text = menuItem.getLocalizedLabel();
		if (text == null || text.length() == 0) {
			text = menuItem.getLabel();
		}
		if ((text != null && text.length() != 0)
				|| (menuItem instanceof MHandledMenuItem) || menuItem.getWidget() != null) {
			IContributionItem contributionItem;
			if (menuItem instanceof MMenu) {
				contributionItem = menuMngrRenderer.getManager((MMenu) menuItem);
			} else {
				contributionItem = menuMngrRenderer.getContribution(menuItem);
			}
			if (dynamicEntry != null
					&& contributionItem.equals(dynamicEntry.getIContributionItem())) {
				dynamicEntry.addCurrentItem((MenuItem) menuItem.getWidget());
			} else {
				ImageDescriptor iconDescriptor = null;
				String iconURI = menuItem.getIconURI();
				if (iconURI != null && iconURI.length() > 0) {
					iconDescriptor = resUtils.imageDescriptorFromURI(URI.createURI(iconURI));
