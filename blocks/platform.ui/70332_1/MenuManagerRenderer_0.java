	private void updateContributionItem(Object element) {
		MMenuItem itemModel = (MMenuItem) element;
		IContributionItem ici = getContribution(itemModel);
		if (ici != null) {
			ici.update();
		}
	}



