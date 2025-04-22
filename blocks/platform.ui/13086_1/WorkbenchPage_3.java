		SaveablesList saveablesList = null;
		Object postCloseInfo = null;
		if (partsToSave.size() > 0) {
			saveablesList = (SaveablesList) getWorkbenchWindow().getService(
					ISaveablesLifecycleListener.class);
			postCloseInfo = saveablesList.preCloseParts(partsToSave, true,
					this.getWorkbenchWindow());
			if (postCloseInfo == null) {
				legacyWindow.firePerspectiveChanged(this, desc, CHANGE_RESET_COMPLETE);
				return;
