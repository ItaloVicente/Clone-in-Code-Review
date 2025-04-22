		IPreferenceStore store = getIDEPreferenceStore();

		store.setValue(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD,
				autoSaveAllButton.getSelection());

		description.setBuildOrder(buildOrder);
		description.setMaxBuildIterations(maxItersField.getIntValue());
		try {
			getWorkspace().setDescription(description);
		} catch (CoreException exception) {
			return false;
		}

		if (!useDefault || (useDefault != defaultOrderInitiallySelected)) {
			defaultOrderInitiallySelected = useDefault;
			if (ResourcesPlugin.getWorkspace().isAutoBuilding()) {
				GlobalBuildAction action = new GlobalBuildAction(workbench
						.getActiveWorkbenchWindow(),
						IncrementalProjectBuilder.INCREMENTAL_BUILD);
				action.doBuild();
			}
		}

		customBuildOrder = null;

		return true;
	}

	private void removeSelection() {

		this.buildList.remove(this.buildList.getSelectionIndices());
	}

	private void setBuildOrderWidgetsEnablement(boolean value) {

