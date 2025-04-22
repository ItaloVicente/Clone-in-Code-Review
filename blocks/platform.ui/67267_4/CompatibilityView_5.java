	private void clearOpaqueToolBarItems(ToolBarManagerRenderer tbmr, MToolBar toolbar) {
		for (Iterator<MToolBarElement> it = toolbar.getChildren().iterator(); it.hasNext();) {
			MToolBarElement element = it.next();
			IContributionItem contribution = tbmr.getContribution(element);
			if (contribution != null) {
				tbmr.clearModelToContribution(element, contribution);
			}
			if (OpaqueElementUtil.isOpaqueToolItem(element)) {
				IContributionItem item = tbmr.getContribution(element);
				if (item != null) {
					tbmr.clearModelToContribution(element, item);
				}
				OpaqueElementUtil.clearOpaqueItem(element);
				it.remove();
			}
		}
	}

