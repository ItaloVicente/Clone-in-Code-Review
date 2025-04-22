		if (UIEvents.UIElement.VISIBLE.equals(attName)) {
			if (element instanceof MMenu) {
				MMenu menuModel = (MMenu) element;
				MenuManager manager = getManager(menuModel);
				if (manager == null) {
					return;
				}
				manager.setVisible(menuModel.isVisible());
				if (manager.getParent() != null) {
					manager.getParent().markDirty();
					scheduleManagerUpdate(manager.getParent());
				}
			} else if (element instanceof MMenuElement) {
				MMenuElement itemModel2 = (MMenuElement) element;
				Object obj2 = getContribution(itemModel2);
				if (!(obj2 instanceof ContributionItem)) {
					return;
				}
				ContributionItem item = (ContributionItem) obj2;
				item.setVisible(itemModel2.isVisible());
				if (item.getParent() != null) {
					item.getParent().markDirty();
					scheduleManagerUpdate(item.getParent());
