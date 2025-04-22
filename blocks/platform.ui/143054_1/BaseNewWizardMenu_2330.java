		if (workbenchWindow != null) {
			unregisterListeners();
			showDlgAction.dispose();
			showDlgAction = null;
			workbenchWindow = null;
			super.dispose();
		}
	}
