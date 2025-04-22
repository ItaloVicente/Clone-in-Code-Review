
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		IContributionManager parent = getParent();
		if (parent instanceof SubToolBarManager) {
			SubToolBarManager subManager = (SubToolBarManager) parent;
			IContributionItem item = subManager.getParent().find(getId());
			if (item instanceof SubContributionItem) {
				item.setVisible(visible);
			}
		}
	}
