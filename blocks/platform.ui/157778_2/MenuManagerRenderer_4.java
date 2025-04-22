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
