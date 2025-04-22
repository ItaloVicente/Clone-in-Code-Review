	public void setWorkingSet(IWorkingSet newWorkingSet) {
		IWorkingSet oldWorkingSet = workingSet;

		workingSet = newWorkingSet;
		if (oldWorkingSet != newWorkingSet) {
			firePropertyChange(CHANGE_WORKING_SET_REPLACE, oldWorkingSet, newWorkingSet);
		}
		if (newWorkingSet != null) {
			WorkbenchPlugin.getDefault().getWorkingSetManager()
					.addPropertyChangeListener(workingSetPropertyChangeListener);
		} else {
			WorkbenchPlugin.getDefault().getWorkingSetManager()
					.removePropertyChangeListener(workingSetPropertyChangeListener);
		}
	}

	public void showActionSet(String actionSetID) {
		Perspective persp = getActivePerspective();
		if (persp != null) {
			ActionSetRegistry reg = WorkbenchPlugin.getDefault().getActionSetRegistry();

			IActionSetDescriptor desc = reg.findActionSet(actionSetID);
			if (desc != null) {
				persp.addActionSet(desc);
				legacyWindow.updateActionSets();
				legacyWindow.firePerspectiveChanged(this, getPerspective(), CHANGE_ACTION_SET_SHOW);
			}
		}
	}

	public IViewPart showView(String viewID) throws PartInitException {
		return showView(viewID, null, VIEW_ACTIVATE);
	}

