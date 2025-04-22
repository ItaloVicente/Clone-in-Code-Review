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
					MMenuElement itemModel = (MMenuElement) element;
					Object obj = getContribution(itemModel);
					if (!(obj instanceof ContributionItem)) {
						return;
					}
					ContributionItem item = (ContributionItem) obj;
					item.setVisible(itemModel.isVisible());
					if (item.getParent() != null) {
						item.getParent().markDirty();
						scheduleManagerUpdate(item.getParent());
					}
