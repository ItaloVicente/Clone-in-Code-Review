		if (workbenchWindow == null) {
			return;
		}
		workbenchWindow.getSelectionService().removeSelectionListener(this);
		workbenchWindow.getPartService().removePartListener(this);
		workbenchWindow = null;
	}
