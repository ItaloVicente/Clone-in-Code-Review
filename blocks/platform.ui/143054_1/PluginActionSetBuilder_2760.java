		return new ActionSetContribution(actionSet.getDesc().getId(), window);
	}

	public static IContributionItem findInsertionPoint(String startId, String sortId, IContributionManager mgr,
			boolean startVsEnd) {
		IContributionItem[] items = mgr.getItems();

		int insertIndex = 0;
		while (insertIndex < items.length) {
			if (startId.equals(items[insertIndex].getId())) {
