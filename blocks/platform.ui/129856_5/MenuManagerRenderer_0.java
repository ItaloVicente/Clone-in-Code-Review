	private void updateChildren(MElementContainer<MUIElement> container) {
		if (container == null)
			return;

		MMenu parentMenu = null;
		Object containerObj = container;
		if (containerObj instanceof MMenu) {
			parentMenu = (MMenu) containerObj;
		}
		MenuManager parentManager = getManager(parentMenu);

		if (parentManager == null) {
			return;
		}

		IContributionItem[] items = parentManager.getItems();
		List<MUIElement> children = container.getChildren();
		for (int index = 0; index < items.length; index++) {
			IContributionItem item = items[index];
			MMenuElement mElem = contributionToModel.get(item);
			if (!children.contains(mElem)) {
				if (mElem == null && item instanceof MenuManager) {
					mElem = managerToModel.get(item);
				}
				if (mElem instanceof MMenu) {
					MMenu menuElement = (MMenu) mElem;
					removeMenuContribution(menuElement);
				} else if (mElem instanceof MDynamicMenuContribution && parentMenu != null) {
					ArrayList<MMenuElement> list = new ArrayList<>();
					list.add(mElem);
					removeDynamicMenuContributions(parentManager, parentMenu, list);
				} else {
					contributionToModel.remove(item);
				}
				parentManager.remove(item);
				clearModelToContribution(mElem, item);
			}
		}
		processContents(container);
	}

