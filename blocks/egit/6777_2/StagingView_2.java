	private static Set<StagingEntry.Action> getAvailableActions(IStructuredSelection selection) {
		Set<StagingEntry.Action> availableActions = EnumSet.noneOf(StagingEntry.Action.class);
		for (Iterator it = selection.iterator(); it.hasNext(); ) {
			StagingEntry stagingEntry = (StagingEntry) it.next();
			if (availableActions.isEmpty())
				availableActions.addAll(stagingEntry.getAvailableActions());
			else
				availableActions.retainAll(stagingEntry.getAvailableActions());
		}
		return availableActions;
	}

