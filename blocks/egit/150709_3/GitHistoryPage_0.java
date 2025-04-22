		private void workAroundBug551067(boolean visible) {
			IContributionManager parent = getParent();
			if (parent instanceof SubToolBarManager) {
				SubToolBarManager subManager = (SubToolBarManager) parent;
				IContributionItem item = subManager.getParent().find(getId());
				if (item instanceof SubContributionItem) {
					item.setVisible(visible && subManager.isVisible());
				}
			}
		}

