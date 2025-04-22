		if (workingSetPropertyChangeListener != null) {
			WorkbenchPlugin.getDefault().getWorkingSetManager()
					.removePropertyChangeListener(workingSetPropertyChangeListener);
			workingSetPropertyChangeListener = null;
		}
		actionBars = null;
		actionSets = null;
		actionSwitcher.activePart = null;
		actionSwitcher.topEditor = null;
		activationList.clear();
		aggregateWorkingSet = null;
		application = null;
		broker = null;
		childrenHandler = null;
		composite = null;
		firingHandler = null;
		input = null;
		legacyWindow = null;
		navigationHistory = null;
		pageChangedListener = null;
		partBeingActivated = null;
		partEvents.clear();
		partService = null;
		referenceRemovalEventHandler = null;
		selectionHandler = null;
		selectionService = null;
		sortedPerspectives.clear();
		tracker = null;
		widgetHandler = null;
		workingSet = null;
